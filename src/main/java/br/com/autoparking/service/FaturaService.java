package br.com.autoparking.service;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Servico;

public interface FaturaService {
    Fatura gerarFaturaPadrao(Order order, Servico[] servicos);
}
