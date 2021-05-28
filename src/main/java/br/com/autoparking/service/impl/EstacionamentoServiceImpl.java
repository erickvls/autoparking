package br.com.autoparking.service.impl;

import br.com.autoparking.model.Endereco;
import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoDTO;
import br.com.autoparking.model.dto.EstacionamentoForm;
import br.com.autoparking.repository.EstacionamentoRepository;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.exception.SalvarEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionamentoServiceImpl implements EstacionamentoService {

    @Autowired
    private EstacionamentoRepository  estacionamentoRepository;

    @Override
    @Transactional
    public void salvarEstacionamento(EstacionamentoForm estacionamentoForm, Usuario usuario) {
        try{
            Estacionamento estacionamento = mapToEntity(estacionamentoForm,usuario);
            estacionamentoRepository.save(estacionamento);
        }catch(Exception e){
            throw new SalvarEntidadeException("Falha ao salvar um novo estacionamento", e);
        }
    }

    @Override
    public List<EstacionamentoDTO> listar() {
        List<EstacionamentoDTO> list = estacionamentoRepository.findAll().stream()
                .map(v-> EstacionamentoDTO.builder()
                        .capacidade(v.getQuantidadeVagas())
                        .nome(v.getNome())
                        .eixoX(v.getEixoX())
                        .eixoY(v.getEixoY())
                        .horarioAbre(v.getHorarioAbre())
                        .horarioFecha(v.getHorarioFecha())
                        .build()).collect(Collectors.toList());
        return list;
    }

    private Estacionamento mapToEntity(EstacionamentoForm estacionamentoForm, Usuario usuario){
        Endereco endereco = Endereco.builder()
                .estado(estacionamentoForm.getEstado())
                .bairro(estacionamentoForm.getBairro())
                .cidade(estacionamentoForm.getCidade())
                .rua(estacionamentoForm.getRua())
                .build();

        return Estacionamento.builder()
                .nome(estacionamentoForm.getNome())
                .usuario(usuario)
                .eixoX(estacionamentoForm.getEixoX())
                .eixoY(estacionamentoForm.getEixoY())
                .horarioAbre(estacionamentoForm.getHorarioAbre())
                .horarioFecha(estacionamentoForm.getHorarioFecha())
                .quantidadeVagas(estacionamentoForm.getQuantidadeVagas())
                .telefone(estacionamentoForm.getTelefone())
                .endereco(endereco)
                .build();
    }
}
