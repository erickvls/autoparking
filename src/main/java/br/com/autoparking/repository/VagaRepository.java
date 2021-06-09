package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Vaga;
import br.com.autoparking.model.enums.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga,Long> {
    List<Vaga> findByEstacionamento(Estacionamento estacionamento);
}
