package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

public interface UsuarioService {
    Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario);
    Usuario criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider);
    Usuario encontrarUsuarioPorUserName(String username);
    boolean gerarSenhaUsuario(String userName, RedirectAttributes redirectAttributes);
    void usuarioMudaSenhaQuandoResetada(String usuario,String senhaAlterada, RedirectAttributes redirectAttribute);
}
