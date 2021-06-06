package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Vaga;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.VagaRepository;
import br.com.autoparking.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaServiceImpl implements VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Override
    public List<Vaga> encontrarPorStatusVagaEEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento){
        return vagaRepository.findByStatusAndEstacionamento(statusVaga,estacionamento);
    }

    @Override
    public Vaga reservarVaga(Vaga vaga){
        vaga.setStatus(StatusVaga.RESERVADO);
        return vagaRepository.save(vaga);
    }
}
