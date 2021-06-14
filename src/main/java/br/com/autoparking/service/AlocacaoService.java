package br.com.autoparking.service;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.StatusVaga;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AlocacaoService {
    String alocarVagaPorEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                         String dataPrevistaEntrada, String dataPrevistaSaida, Carro carro);
    Alocacao retonaAlocacaoPorOrder(Order order);
    String alocarVagaReservaAdmin(Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                  String dataPrevistaSaida, Carro carro);
}
