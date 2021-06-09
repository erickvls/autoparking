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

    VagaHorario reservarVagaHorario(VagaHorario vagaHorario, LocalDateTime horaChegada,LocalDateTime horaSaida){
        Vaga vagaS = vagaService.reservarVaga(vagaHorario.getVaga());
        VagaHorario vH = VagaHorario.builder().vaga(vagaS)
                .statusVaga(StatusVaga.RESERVADO)
                .horaChegada(horaChegada)
                .horaSaida(horaSaida)
                .build();

        return vagaHorarioRepository.save(vagaHorario);
    }
}
