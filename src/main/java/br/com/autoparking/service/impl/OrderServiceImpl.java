package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.StatusOrder;
import br.com.autoparking.repository.OrderRepository;
import br.com.autoparking.service.OrderService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDateTime dataPrevistaEntrada,LocalDateTime dataPrevistaSaida){
        Order order = Order.builder()
                .dataOrder(LocalDateTime.now())
                .statusOrder(StatusOrder.EM_ABERTO)
                .dataPrevistaEntrada(dataPrevistaEntrada)
                .dataPrevistaSaída(dataPrevistaSaida)
                .estacionamento(estacionamento)
                .usuario(usuario)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public boolean usuarioPossuiOrderAberta(Usuario usuario, Estacionamento estacionamento) {
        List<Order> order = orderRepository.findByUsuarioAndEstacionamento(usuario,estacionamento);
        List<Order> emAbertoOuEmAndamento = order.stream()
                    .filter(o->o.getStatusOrder().equals(StatusOrder.ANDAMENTO) || o.getStatusOrder().equals(StatusOrder.EM_ABERTO))
                        .collect(Collectors.toList());
        return emAbertoOuEmAndamento.size() > 0;
    }

    @Override
    public Map<LocalDateTime, LocalDateTime> listarOrderHorariosOcupados(Estacionamento estacionamento){
        List<Order> ordersAbertasOuAndamento = orderRepository.findByEstacionamentoAndStatusOrderIsEmAbertoOrEmAndamento(estacionamento);
        return ordersAbertasOuAndamento.stream()
                .collect(Collectors.toMap(Order::getDataPrevistaEntrada, Order::getDataPrevistaSaída));
    }

}
