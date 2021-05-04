package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;

public interface UsuarioService {
    void criarNovoUsuarioFormularioRegistro(Usuario usuario);
    void criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider);
    boolean usuariosIsAvailable(String username);

}
