package br.com.autoparking.repository;

import br.com.autoparking.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {
    Optional<Usuario> findByUserName(String username);
}
