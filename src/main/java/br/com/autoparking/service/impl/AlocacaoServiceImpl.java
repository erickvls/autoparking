package br.com.autoparking.service.impl;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.AlocacaoRepository;
import br.com.autoparking.repository.VagaRepository;
import br.com.autoparking.service.AlocacaoService;
import br.com.autoparking.service.OrderService;
import br.com.autoparking.service.VagaService;
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


    private LocalDateTime dataHoraLimite = LocalDateTime.now(ZoneId.systemDefault()).plusHours(5);

    @Override
    @Transactional
    public String alocarVagaPorEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                                String dataPrevistaEntrada,String dataPrevistaSaida, Carro carro){



        if(!validarData(dataPrevistaEntrada)){
            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu  HH:mm");
            System.out.println(dataHoraLimite);
            redirectAttributes.addFlashAttribute("mensagemError", "Você só pode reservar uma vaga até: "+dTF.format(dataHoraLimite));
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }
        if(dataSaidaEMaiorQueEntrada(dataPrevistaEntrada,dataPrevistaSaida)){
            redirectAttributes.addFlashAttribute("mensagemError", "A Data/hora de saída não pode ser inferior a entrada");
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }

        //if(horarioEstaOcupado(estacionamento,converterDataString(dataPrevistaEntrada),converterDataString(dataPrevistaSaida))){

        //}
        List<Vaga> vagasDisponiveis = vagaService.encontrarPorStatusVagaEEstacionamento(statusVaga,estacionamento);

        if(vagasDisponiveis.size()<1){
            redirectAttributes.addFlashAttribute("mensagemError", "Estacionamento não possui vagas disponíveis");
            return "redirect:/home/estacionamento/visualizar/"+estacionamento.getId();
        }

        Vaga vagaEstacionamento = vagaService.reservarVaga(vagasDisponiveis.get(0));
        Order order = orderService.criarOrderPeloCliente(estacionamento,usuario,converterDataString(dataPrevistaEntrada),converterDataString(dataPrevistaSaida));
        Alocacao alocacao = Alocacao.builder()
                .carro(carro)
                .order(order)
                .vaga(vagaEstacionamento)
                .build();
        alocacaoRepository.save(alocacao);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sua vaga foi reservada. Para visualizá-la acesse o menu Perfil > Orders.");
        return "redirect:/home/";
    }


    private boolean validarData(String dataPrevista){
        LocalDateTime dataHoraSelecionada = converterDataString(dataPrevista);
        return dataHoraSelecionada.isBefore(dataHoraLimite);
    }

    private boolean dataSaidaEMaiorQueEntrada(String dataPrevistaEntrada,String dataPrevistaSaida){
        LocalDateTime dataHoraSelecionadaentrada = converterDataString(dataPrevistaEntrada);
        LocalDateTime dataHoraSelecionadaSaida = converterDataString(dataPrevistaSaida);
        return dataHoraSelecionadaSaida.isBefore(dataHoraSelecionadaentrada);
    }

    private LocalDateTime converterDataString(String dataPrevista){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataPrevista.replace("T"," ").substring(0,16));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private boolean horarioEstaOcupado(Estacionamento estacionamento,LocalDateTime dataEntradaPrevista, LocalDateTime dataSaidaPrevista){
        Map<LocalDateTime,LocalDateTime> horariosOcupados = orderService.listarOrderHorariosOcupados(estacionamento);

        for (Map.Entry<LocalDateTime, LocalDateTime> pair : horariosOcupados.entrySet()) {
            if(dataEntradaPrevista.isAfter(pair.getKey()) && dataSaidaPrevista.isBefore(pair.getValue())){
               return true;
            }else if(1){
                return true;
            }
        }
        return false;
    }
}
