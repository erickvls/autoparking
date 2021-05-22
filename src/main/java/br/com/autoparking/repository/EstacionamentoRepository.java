package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EstacionamentoRepository extends JpaRepository<Estacionamento,Long> {
}
