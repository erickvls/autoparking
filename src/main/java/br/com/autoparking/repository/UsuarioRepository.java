package br.com.autoparking.repository;

import br.com.autoparking.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    Optional<Usuario> findByUserName(String username);
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.criador=:usuario")
    long qtdGestoresPorAdmin(Usuario usuario);
    List<Usuario> findByCriador(Usuario usuario);
}
