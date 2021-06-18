package br.com.autoparking.service.impl;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.StatusOrder;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.OrderRepository;
import br.com.autoparking.service.AlocacaoService;
import br.com.autoparking.service.OrderService;
import br.com.autoparking.service.VagaService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VagaService vagaService;

    @Autowired
    private AlocacaoService alocacaoService;

    @Autowired
    private VagaHorarioServiceImpl vagaHorarioService;

    @Override
    public Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDateTime dataPrevistaEntrada,LocalDateTime dataPrevistaSaida,
                                        VagaHorario vagaHorario){
        Order order = Order.builder()
                .dataOrder(LocalDateTime.now())
                .statusOrder(StatusOrder.EM_ABERTO)
                .dataPrevistaEntrada(dataPrevistaEntrada)
                .dataPrevistaSaida(dataPrevistaSaida)
                .estacionamento(estacionamento)
                .usuario(usuario)
                .vagaHorario(vagaHorario)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Order criarOrderPeloAdmin(Estacionamento estacionamento,Usuario usuario,LocalDateTime dataPrevistaSaida,VagaHorario vagaHorario){
        Order order = Order.builder()
                .dataOrder(LocalDateTime.now())
                .statusOrder(StatusOrder.ANDAMENTO)
                .dataEntrada(LocalDateTime.now())
                .dataPrevistaSaida(dataPrevistaSaida)
                .estacionamento(estacionamento)
                .usuario(usuario)
                .vagaHorario(vagaHorario)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public boolean usuarioPossuiOrderAbertaOuAndamento(Usuario usuario, Estacionamento estacionamento) {
        List<Order> order = orderRepository.findByUsuarioAndEstacionamento(usuario,estacionamento);
        List<Order> emAbertoOuEmAndamento = order.stream()
                    .filter(o->o.getStatusOrder().equals(StatusOrder.ANDAMENTO) || o.getStatusOrder().equals(StatusOrder.EM_ABERTO))
                        .collect(Collectors.toList());
        return emAbertoOuEmAndamento.size() > 0;
    }

    @Override
    public Order usuarioPossuiReserva(Usuario usuario, Estacionamento estacionamento){
        List<Order> order = orderRepository.findByUsuarioAndEstacionamento(usuario,estacionamento);
        if(order.isEmpty()){
            return new Order();
        }
        Order reservaEmAberto = order.stream()
                .filter(o->o.getStatusOrder().equals(StatusOrder.EM_ABERTO)).findFirst().get();
        return reservaEmAberto;
    }

    @Override
    public Map<LocalDateTime, LocalDateTime> listarOrderHorariosOcupados(Estacionamento estacionamento){
        List<Order> ordersAbertasOuAndamento = orderRepository.findByEstacionamentoAndStatusOrderIsEmAbertoOrEmAndamento(estacionamento);
        return ordersAbertasOuAndamento.stream()
                .collect(Collectors.toMap(Order::getDataPrevistaEntrada, Order::getDataPrevistaSaida));
    }

    @Override
    public List<Order> listarTodasOrdersDeUmUsuario(Usuario usuario) {
        return orderRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public Order mudarStatusOrdemComDataEntrada(Order order){
        Alocacao alocacao =  alocacaoService.retonaAlocacaoPorOrder(order);
        vagaService.reservarVaga(StatusVaga.OCUPADO,alocacao.getVaga());
        VagaHorario vagaHorario = order.getVagaHorario();
        vagaHorarioService.mudarStatusVagaHorario(StatusVaga.OCUPADO,vagaHorario);
        order.setDataEntrada(LocalDateTime.now());
        order.setStatusOrder(StatusOrder.ANDAMENTO);
        return orderRepository.save(order);
    }

}
