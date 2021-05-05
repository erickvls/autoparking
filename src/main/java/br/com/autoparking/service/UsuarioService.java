package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;

import java.util.Optional;

public interface UsuarioService {
    Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario);
    Usuario criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider);
    Usuario atualizarUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider google);
    Usuario encontrarUsuarioPorUserName(String username);

}
