package br.com.autoparking.service.impl;

import br.com.autoparking.model.Vaga;
import br.com.autoparking.model.VagaHorario;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.VagaHorarioRepository;
import br.com.autoparking.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VagaHorarioServiceImpl {

    @Autowired
    private VagaHorarioRepository vagaHorarioRepository;

    @Autowired
    private VagaService vagaService;

    VagaHorario reservarVagaHorario(StatusVaga statusVaga,VagaHorario vagaHorario, LocalDateTime horaChegada,LocalDateTime horaSaida){
        vagaService.reservarVaga(statusVaga,vagaHorario.getVaga());
        return vagaHorarioRepository.save(vagaHorario);
    }

    VagaHorario mudarStatusVagaHorario(StatusVaga statusVaga, VagaHorario vagaHorario){
        vagaHorario.setStatusVaga(statusVaga);
        return vagaHorarioRepository.save(vagaHorario);
    }
}
