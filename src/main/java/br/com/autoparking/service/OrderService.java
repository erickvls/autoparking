package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDateTime dataPrevistaEntrada, LocalDateTime dataPrevistaSaida);
    boolean usuarioPossuiOrderAbertaOuAndamento(Usuario usuario,Estacionamento estacionamento);
    Map<LocalDateTime, LocalDateTime> listarOrderHorariosOcupados(Estacionamento estacionamento);
    List<Order> listarTodasOrdersDeUmUsuario(Usuario usuario);
    Order usuarioPossuiReserva(Usuario usuario,Estacionamento estacionamento);
}
