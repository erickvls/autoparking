package br.com.autoparking.service;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Order;

public interface FaturaService {
    Fatura gerarFaturaPadrao(Order order);
}
