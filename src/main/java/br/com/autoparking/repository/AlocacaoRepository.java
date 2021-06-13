package br.com.autoparking.repository;

import br.com.autoparking.model.Alocacao;
import br.com.autoparking.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlocacaoRepository extends JpaRepository<Alocacao,Long> {
    Alocacao findByOrder(Order order);
}
