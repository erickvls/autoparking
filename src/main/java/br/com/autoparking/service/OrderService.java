package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;

import java.time.LocalDate;
import java.util.Date;

public interface OrderService {
    Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDate dataPrevista);
}
