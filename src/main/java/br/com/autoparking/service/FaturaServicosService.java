package br.com.autoparking.service;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.FaturaServicos;
import br.com.autoparking.model.Servico;

public interface FaturaServicosService {
    FaturaServicos salvar(Fatura fatura, Servico servico);
}
