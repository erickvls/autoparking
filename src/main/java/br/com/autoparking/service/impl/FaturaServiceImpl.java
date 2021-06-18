package br.com.autoparking.service.impl;

import br.com.autoparking.model.*;
import br.com.autoparking.model.dto.ServicoFormDTO;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.repository.FaturaRepository;
import br.com.autoparking.service.FaturaService;
import br.com.autoparking.service.FaturaServicosService;
import br.com.autoparking.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FaturaServiceImpl implements FaturaService {


    @Autowired
    private ServicoService servicoService;

    @Autowired
    private FaturaServicosService faturaServicosService;

    @Autowired
    private FaturaRepository faturaRepository;
    @Override
    public Fatura gerarFaturaPadrao(Order order) {

        Fatura fat = faturaRepository.save(Fatura.builder().data(new Date())
                .order(order)
                .total(new BigDecimal(0))
                .build());
        Estacionamento estacionamento = order.getEstacionamento();
        Set<Servico> servico = estacionamento.getServicos();
        adicionaServicoPadrao(fat,servico,order);
        return fat;
    }



    private Set<FaturaServicos> adicionaServicoPadrao(Fatura fatura,Set<Servico> servico,Order order){
        Set<FaturaServicos> faturaServicos = new HashSet<>();
       for(Servico srv: servico){
          if(srv.getTipoServico().equals(TipoServico.HORA)){
              faturaServicos.add(faturaServicosService.salvar(fatura,srv));
          }
           if(srv.getTipoServico().equals(TipoServico.RESERVA)){
               faturaServicos.add(faturaServicosService.salvar(fatura,srv));
           }
       }
       return faturaServicos;
    }

}
