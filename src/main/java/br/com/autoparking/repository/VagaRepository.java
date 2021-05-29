package br.com.autoparking.repository;

import br.com.autoparking.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga,Long> {
}
