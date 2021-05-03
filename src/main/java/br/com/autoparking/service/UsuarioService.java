package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import org.hibernate.mapping.UnionSubclass;

import java.util.Optional;

public interface UsuarioService {
    void salvarUsuario(Usuario usuario);
}
