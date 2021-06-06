package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento,Long> {
}
