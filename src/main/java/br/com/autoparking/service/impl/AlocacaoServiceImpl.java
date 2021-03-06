package br.com.autoparking.service.impl;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.AlocacaoRepository;
import br.com.autoparking.repository.VagaHorarioRepository;
import br.com.autoparking.repository.VagaRepository;
import br.com.autoparking.service.AlocacaoService;
import br.com.autoparking.service.OrderService;
import br.com.autoparking.service.VagaService;
import br.com.autoparking.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static br.com.autoparking.service.utils.Utils.converterDataString;
import static br.com.autoparking.service.utils.Utils.dataSaidaEMaiorQueEntrada;

@Service
public class AlocacaoServiceImpl implements AlocacaoService {

    @Autowired
    private VagaService vagaService;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AlocacaoRepository alocacaoRepository;

    @Autowired
    private VagaHorarioRepository vagaHorarioRepository;

    @Autowired
    private VagaHorarioServiceImpl vagaHorarioService;

    private LocalDateTime dataHoraLimite = LocalDateTime.now(ZoneId.systemDefault()).plusHours(5);

    @Override
    @Transactional
    public String alocarVagaPorEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                                String dataPrevistaEntrada,String dataPrevistaSaida, Carro carro){

        if(Objects.isNull(usuario.getFormaPagamento())){
            redirectAttributes.addFlashAttribute("mensagemError", "Voc?? deve adicionar o n??mero de cart??o para poder reservar uma vaga.");
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }

        if(!validarData(dataPrevistaEntrada)){
            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu  HH:mm");
            System.out.println(dataHoraLimite);
            redirectAttributes.addFlashAttribute("mensagemError", "Voc?? s?? pode reservar uma vaga at??: "+dTF.format(dataHoraLimite));
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }
        if(dataSaidaEMaiorQueEntrada(dataPrevistaEntrada,dataPrevistaSaida)){
            redirectAttributes.addFlashAttribute("mensagemError", "A Data/hora de sa??da n??o pode ser inferior a entrada");
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }
        VagaHorario vagaHorario = horarioEstaLivre(StatusVaga.RESERVADO,estacionamento, converterDataString(dataPrevistaEntrada), converterDataString(dataPrevistaSaida));
        if(Objects.isNull(vagaHorario.getStatusVaga())){
            redirectAttributes.addFlashAttribute("mensagemError", "N??o h?? vagas dispon??veis para o hor??rio selecionado.");
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }

        VagaHorario vagaEstacionamento = vagaHorarioService.reservarVagaHorario(StatusVaga.RESERVADO,vagaHorario, converterDataString(dataPrevistaEntrada),converterDataString(dataPrevistaSaida));

        Order order = orderService.criarOrderPeloCliente(estacionamento,usuario, converterDataString(dataPrevistaEntrada), converterDataString(dataPrevistaSaida),vagaEstacionamento);
        Alocacao alocacao = Alocacao.builder()
                .carro(carro)
                .order(order)
                .vaga(vagaEstacionamento.getVaga())
                .build();
        alocacaoRepository.save(alocacao);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sua vaga foi reservada. Para visualiz??-la acesse o menu Perfil > Orders.");
        return "redirect:/home/";
    }

    @Override
    public String alocarVagaReservaAdmin(Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                         String dataPrevistaSaida, Carro carro){

        if(Objects.isNull(usuario.getFormaPagamento())){
            redirectAttributes.addFlashAttribute("mensagemError", "Voc?? deve adicionar o n??mero de cart??o para poder reservar uma vaga.");
            return "redirect:/admin/estacionamentos";
        }

        VagaHorario vagaHorario = horarioEstaLivre(StatusVaga.OCUPADO,estacionamento,LocalDateTime.now(),converterDataString(dataPrevistaSaida));
        if(Objects.isNull(vagaHorario.getStatusVaga())){
            redirectAttributes.addFlashAttribute("mensagemError", "N??o h?? vagas dispon??veis para o hor??rio selecionado.");
            return "redirect:/admin/estacionamentos";
        }
        VagaHorario vagaEstacionamento = vagaHorarioService.reservarVagaHorario(StatusVaga.OCUPADO,vagaHorario,LocalDateTime.now(),converterDataString(dataPrevistaSaida));

        Order order = orderService.criarOrderPeloAdmin(estacionamento,usuario,converterDataString(dataPrevistaSaida),vagaEstacionamento);
        Alocacao alocacao = Alocacao.builder()
                .carro(carro)
                .order(order)
                .vaga(vagaEstacionamento.getVaga())
                .build();
        alocacaoRepository.save(alocacao);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "A vaga foi alocada. O cliente poder?? entrar no estacionamento");
        return "redirect:/admin/estacionamentos";
    }

    @Override
    public Alocacao retonaAlocacaoPorOrder(Order order){
        return alocacaoRepository.findByOrder(order);
    }


    private boolean validarData(String dataPrevista){
        LocalDateTime dataHoraSelecionada = converterDataString(dataPrevista);
        return dataHoraSelecionada.isBefore(dataHoraLimite);
    }


    private VagaHorario horarioEstaLivre(StatusVaga statusVaga,Estacionamento estacionamento,LocalDateTime dataEntradaPrevista, LocalDateTime dataSaidaPrevista){
        List<Vaga> vagas = vagaService.listarVagasEstacionamento(estacionamento);
        for (int i = 0; i < vagas.size(); i++) {
            List<VagaHorario> horariosVaga = vagaHorarioRepository.findByVaga(vagas.get(i));
            if(horariosVaga.isEmpty()){
                return VagaHorario.builder()
                        .horaChegada(dataEntradaPrevista)
                        .horaSaida(dataSaidaPrevista)
                        .statusVaga(statusVaga)
                        .vaga(vagas.get(i))
                        .build();
            }
            for (VagaHorario vagaHorario : horariosVaga) {
                if(vagaHorario.getStatusVaga().equals(StatusVaga.LIVRE)){
                    return VagaHorario.builder()
                            .horaChegada(dataEntradaPrevista)
                            .horaSaida(dataSaidaPrevista)
                            .statusVaga(statusVaga)
                            .vaga(vagas.get(i))
                            .build();
                }
                if ((dataEntradaPrevista.isBefore(horariosVaga.get(i).getHoraChegada()) && dataSaidaPrevista.isBefore(horariosVaga.get(i).getHoraChegada())) && (dataSaidaPrevista.isBefore(horariosVaga.get(i).getHoraChegada()) && dataSaidaPrevista.isBefore(horariosVaga.get(i).getHoraSaida()))) {
                    return VagaHorario.builder()
                            .horaChegada(dataEntradaPrevista)
                            .horaSaida(dataSaidaPrevista)
                            .statusVaga(statusVaga)
                            .vaga(vagas.get(i))
                            .build();
                } else if ((dataEntradaPrevista.isAfter(horariosVaga.get(i).getHoraChegada()) && dataEntradaPrevista.isAfter(horariosVaga.get(i).getHoraSaida())) && (dataSaidaPrevista.isAfter(horariosVaga.get(i).getHoraChegada())) && dataSaidaPrevista.isAfter(horariosVaga.get(i).getHoraSaida())) {
                    return VagaHorario.builder()
                            .horaChegada(dataEntradaPrevista)
                            .horaSaida(dataSaidaPrevista)
                            .statusVaga(statusVaga)
                            .vaga(vagas.get(i))
                            .build();
                }
            }
        }
        return new VagaHorario();
    }
}
