package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.FaturaDTO;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.service.FaturaService;
import br.com.autoparking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstatisticaServiceImpl {

    @Autowired
    private OrderService orderService;



    public BigDecimal mostrarSaldo(String opcao, Usuario usuario){
        Estacionamento estacionamento = usuario.getEstacionamentos().stream().findFirst().orElse(null);
        LocalDateTime dataTo = LocalDateTime.now();
        LocalDateTime dataFrom = opcaoSelecionada(opcao);
        List<Order> orderList = orderService.mostrarOrderFechadaPorEstacionamento(estacionamento);

        return orderList.stream()
                .filter(valor-> valor.getDataOrder().isBefore(dataTo) && valor.getDataOrder().isAfter(dataFrom))
                .map(v->v.getFatura().getTotal())
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    public List<FaturaDTO> estatisticaSemanal(String dataFrom, String dataTo, Usuario usuario){
        Estacionamento estacionamento = usuario.getEstacionamentos().stream().findFirst().orElse(null);
        LocalDateTime dataFromLocal = converterDataString(dataFrom);
        LocalDateTime dataToLocal = converterDataString(dataTo);

        Map<Integer,List<Fatura>> faturaPorDia =  orderService.mostrarOrderFechadaPorEstacionamento(estacionamento).stream()
                .filter(valor -> valor.getDataOrder().isBefore(dataToLocal) && valor.getDataOrder().isAfter(dataFromLocal))
                .map(Order::getFatura).sorted(Comparator.comparing((Fatura fat) -> fat.getData().toLocalDate())
                        .reversed()
                        .reversed()
                        .thenComparing(Comparator.comparing((Fatura fat) -> fat.getData().toLocalTime())))
                .collect(Collectors.groupingBy(f->f.getData().getDayOfMonth()));


        List<FaturaDTO> faturaDTOList = new ArrayList<>();
        for (Map.Entry<Integer,List<Fatura>> map : faturaPorDia.entrySet()) {
            BigDecimal total = map.getValue().stream().map(Fatura::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
            int qtdfaturaPorDia = map.getValue().size();
            faturaDTOList.add(FaturaDTO.builder().dia(map.getKey()).total(total).faturaPorDia(qtdfaturaPorDia).build());
        }

        return faturaDTOList;
    }


    private LocalDateTime converterDataString(String dataPrevista){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataPrevista.replace("T"," ").substring(0,16));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private LocalDateTime opcaoSelecionada(String data){
        LocalDateTime dataHoje = LocalDateTime.now();
        LocalDateTime dataSolicitada;
        switch (data) {
            case "30_DIAS" -> {
                dataSolicitada = dataHoje.minusDays(30);
            }
            case "90_DIAS" -> {
                dataSolicitada = dataHoje.minusDays(90);
            }
            case "180_DIAS" -> {
                dataSolicitada = dataHoje.minusDays(180);
            }
            case "365_DIAS" -> {
                dataSolicitada = dataHoje.minusDays(365);
            }
            default -> throw new IllegalStateException("Unexpected value: " + data);
        }
        return dataSolicitada;
    }



}
