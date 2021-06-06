package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
