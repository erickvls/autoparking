package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estado;
import br.com.autoparking.repository.EstadoRepository;
import br.com.autoparking.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionamentoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;
    @Override
    public List<Estado> listarTodosEstados() {
        return estadoRepository.findAll();
    }
}
