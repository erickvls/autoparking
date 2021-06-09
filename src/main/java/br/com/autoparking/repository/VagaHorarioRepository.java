package br.com.autoparking.repository;

import br.com.autoparking.model.Vaga;
import br.com.autoparking.model.VagaHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaHorarioRepository extends JpaRepository<VagaHorario,Long> {
    List<VagaHorario> findByVaga(Vaga vaga);
}
