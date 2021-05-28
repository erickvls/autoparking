package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoDTO;
import br.com.autoparking.model.dto.EstacionamentoForm;

import java.util.List;

public interface EstacionamentoService {
    void salvarEstacionamento(EstacionamentoForm estacionamentoForm, Usuario usuario);
    List<EstacionamentoDTO> listar();
}
