package br.com.autoparking.service.impl;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.FaturaServicos;
import br.com.autoparking.model.Servico;
import br.com.autoparking.repository.FaturaServicoRepository;
import br.com.autoparking.service.FaturaServicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaturaServicosServiceImpl implements FaturaServicosService {


    @Autowired
    private FaturaServicoRepository faturaServicoRepository;

    @Override
    public FaturaServicos salvar(Fatura fatura, Servico servico) {
        FaturaServicos fat = FaturaServicos.builder().servico(servico).fatura(fatura).build();
        return faturaServicoRepository.save(fat);
    }
}
