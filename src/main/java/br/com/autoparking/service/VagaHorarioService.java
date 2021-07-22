package br.com.autoparking.service;

import br.com.autoparking.model.VagaHorario;
import br.com.autoparking.model.enums.StatusVaga;

import java.time.LocalDateTime;

public interface VagaHorarioService {
    VagaHorario reservarVagaHorario(StatusVaga statusVaga, VagaHorario vagaHorario, LocalDateTime horaChegada, LocalDateTime horaSaida);
    VagaHorario mudarStatusVagaHorario(StatusVaga statusVaga, VagaHorario vagaHorario);
}
