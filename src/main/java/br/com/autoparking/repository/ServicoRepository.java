package br.com.autoparking.repository;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Servico;
import br.com.autoparking.model.enums.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico,Long> {
    @Query("SELECT s FROM Servico s WHERE " + "s.estacionamento = :estacionamento "
            + "AND s.tipoServico like :tipoServico")
    Servico findByEstacionamentoAndTipoServico(Estacionamento estacionamento, TipoServico tipoServico);
}
