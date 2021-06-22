package br.com.autoparking.service.impl;

import br.com.autoparking.model.*;
import br.com.autoparking.model.dto.ServicoFormDTO;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.repository.FaturaRepository;
import br.com.autoparking.service.FaturaService;
import br.com.autoparking.service.FaturaServicosService;
import br.com.autoparking.service.OrderService;
import br.com.autoparking.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class FaturaServiceImpl implements FaturaService {


    @Autowired
    private ServicoService servicoService;

    @Autowired
    private FaturaServicosService faturaServicosService;

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private OrderService orderService;

    @Transactional
    @Override
    public Fatura gerarFaturaPadrao(Order order, Servico [] servicosArray) {

        Fatura fat = Fatura.builder().data(new Date())
                .order(order)
                .total(new BigDecimal(0))
                .build();
        fat = faturaRepository.save(fat);
        order.setFatura(fat);
        Set<FaturaServicos> faturaServicos = adicionaServicos(fat,servicosArray,order);
        fat.setFaturaServicos(faturaServicos);
        Order orderNova = orderService.fecharOrder(order);
        fat.setOrder(orderNova);
        calcularValorFatura(fat);
        faturaRepository.save(fat);
        return fat;
    }



    private Set<FaturaServicos> adicionaServicos(Fatura fatura,Servico[] servicos,Order order){

        Set<FaturaServicos> faturaServicos = new HashSet<>();
        if(!Objects.isNull(servicos)) {
            for (Servico servico : servicos) {
                faturaServicos.add(FaturaServicos.builder().fatura(fatura).servico(servico).build());
            }
        }
        if(!Objects.isNull(order.getDataPrevistaEntrada())){
            faturaServicos.add(FaturaServicos.builder().fatura(fatura).servico(order.getEstacionamento().getServicos().stream()
                    .filter(s->s.getTipoServico().equals(TipoServico.RESERVA)).findFirst().orElse(null)).build());
        }

        faturaServicos.add(FaturaServicos.builder().fatura(fatura).servico(order.getEstacionamento().getServicos().stream()
                    .filter(s->s.getTipoServico().equals(TipoServico.HORA)).findFirst().orElse(null)).build());

        return faturaServicos;
    }

    private Fatura calcularValorFatura(Fatura fatura){
       Set<FaturaServicos> faturaServicos = fatura.getFaturaServicos();
       BigDecimal total = new BigDecimal(0);
       for(FaturaServicos faturaServico: faturaServicos){
           if(faturaServico.getServico().getTipoServico().equals(TipoServico.OUTRO)){
               total = total.add(faturaServico.getServico().getValor());
           }else if(faturaServico.getServico().getTipoServico().equals(TipoServico.RESERVA)){
               total = total.add(faturaServico.getServico().getValor());
           }else if(faturaServico.getServico().getTipoServico().equals(TipoServico.HORA)) {
               long duracao =  fatura.getOrder().getDuracao();
               BigDecimal valor = faturaServico.getServico().getValor().multiply(new BigDecimal(duracao));
               total = valor.divide(new BigDecimal(60)).add(total);
           }
       }
       fatura.setTotal(total);
       return fatura;
    }

}
