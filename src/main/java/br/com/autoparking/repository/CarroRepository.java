package br.com.autoparking.repository;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro,Long> {
    List<Carro> findCarroByUsuarioAndExcluidoIsFalse(Usuario usuario);
    Carro findCarroByUsuarioAndId(Usuario usuario,Long id );
}
