package br.com.autoparking.service;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Vaga;
import br.com.autoparking.model.enums.StatusVaga;

import java.util.List;

public interface VagaService {
    List<Vaga> listarVagasEstacionamento(Estacionamento estacionamento);
    Vaga reservarVaga(StatusVaga statusVaga,Vaga vaga);
}
