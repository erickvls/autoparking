package br.com.autoparking.service.impl;

import br.com.autoparking.model.Servico;
import br.com.autoparking.repository.ServicoRepository;
import br.com.autoparking.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public void salvar(Servico servico){
        servicoRepository.save(servico);
    }
}
