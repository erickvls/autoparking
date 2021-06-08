package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUsuarioAndEstacionamento(Usuario usuario, Estacionamento estacionamento);

    @Query("SELECT o FROM Order o WHERE " + "o.estacionamento = :estacionamento "
            + "AND o.statusOrder like 'EM_ABERTO' OR o.statusOrder = 'ANDAMENTO'")
    List<Order> findByEstacionamentoAndStatusOrderIsEmAbertoOrEmAndamento(@Param("estacionamento") Estacionamento estacionamento);
}
