package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.StatusOrder;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.repository.OrderRepository;
import br.com.autoparking.service.AlocacaoService;
import br.com.autoparking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order criarOrderPeloCliente(Estacionamento estacionamento, Usuario usuario, LocalDate dataPrevista){
        Order order = Order.builder()
                .dataOrder(LocalDate.now())
                .statusOrder(StatusOrder.EM_ABERTO)
                .dataPrevista(dataPrevista)
                .estacionamento(estacionamento)
                .usuario(usuario)
                .build();
        return orderRepository.save(order);
    }
}
