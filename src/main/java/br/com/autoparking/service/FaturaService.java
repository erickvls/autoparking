package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Servico;

import java.util.List;

public interface FaturaService {
    Fatura gerarFaturaPadrao(Order order, Servico[] servicos);
}
