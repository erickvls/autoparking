package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public interface OrderService {
    Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDateTime dataPrevistaEntrada, LocalDateTime dataPrevistaSaida);
    boolean usuarioPossuiOrderAberta(Usuario usuario,Estacionamento estacionamento);
    Map<LocalDateTime, LocalDateTime> listarOrderHorariosOcupados(Estacionamento estacionamento);
}
