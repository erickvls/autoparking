package br.com.autoparking.repository;

import br.com.autoparking.model.FaturaServicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaturaServicoRepository extends JpaRepository<FaturaServicos,Long> {
}
