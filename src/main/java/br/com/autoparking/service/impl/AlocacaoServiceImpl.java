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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    @Override
    public Alocacao alocarVagaPorEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                                LocalDate dataPrevista, Carro carro){
        List<Vaga> vagasDisponiveis = vagaService.encontrarPorStatusVagaEEstacionamento(statusVaga,estacionamento);

        if(vagasDisponiveis.size()<1){
            redirectAttributes.addFlashAttribute("mensagemError", "Estacionamento não possui vagas disponíveis");
        }

        Vaga vagaEstacionamento = vagaService.reservarVaga(vagasDisponiveis.get(0));
        Order order = orderService.criarOrderPeloCliente(estacionamento,usuario,dataPrevista);
        Alocacao alocacao = Alocacao.builder()
                .carro(carro)
                .order(order)
                .vaga(vagaEstacionamento)
                .build();
        return alocacaoRepository.save(alocacao);
    }
}
