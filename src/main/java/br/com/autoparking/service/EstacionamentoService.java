package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;

public interface EstacionamentoService {
    void salvarEstacionamento(EstacionamentoForm estacionamentoForm, Usuario usuario);
}
