package br.com.autoparking.service;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.AuthenticationProvider;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario,String role);
    Usuario criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider);
    Usuario encontrarUsuarioPorUserName(String username);
    Usuario encontrarUsuarioPorId(long id);
    Usuario criarNovoGestor(Usuario criador,Usuario usuario,String role);
    void salvarUsuario(Usuario usuario);
    boolean gerarSenhaUsuario(String userName, RedirectAttributes redirectAttributes);
    void usuarioMudaSenhaQuandoResetada(String usuario,String senhaAlterada, RedirectAttributes redirectAttribute);
    void editarPerfil(UsuarioEditarPerfil usuarioEditarPerfil);
    long quantidadeGestoresPorEstacionamento(Usuario usuario);
    List<Usuario> listarGestores(Usuario usuario);
}
