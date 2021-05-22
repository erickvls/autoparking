package br.com.autoparking.service.impl;

import br.com.autoparking.model.Endereco;
import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Estado;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;
import br.com.autoparking.repository.EstacionamentoRepository;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.exception.FalhaAoSalvarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
            throw new FalhaAoSalvarException("asdasd", e);
        }
    }

    private Estacionamento mapToEntity(EstacionamentoForm estacionamentoForm, Usuario usuario){
        return Estacionamento.builder()
                .nome(estacionamentoForm.getNome())
                .usuario(usuario)
                .eixoX(estacionamentoForm.getEixoX())
                .eixoY(estacionamentoForm.getEixoY())
                .horarioAbre(estacionamentoForm.getHorarioAbre())
                .horarioFecha(estacionamentoForm.getHorarioFecha())
                .quantidadeVagas(estacionamentoForm.getQuantidadeVagas())
                .telefone(estacionamentoForm.getTelefone())
                .build();
    }
}
