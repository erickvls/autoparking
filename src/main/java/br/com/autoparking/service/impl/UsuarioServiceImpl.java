package br.com.autoparking.service.impl;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.repository.RoleRepository;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.service.CryptService;
import br.com.autoparking.service.EmailService;
import br.com.autoparking.service.UsuarioService;
import br.com.autoparking.service.exception.SalvarEntidadeException;
import br.com.autoparking.service.exception.MappearEntidadeException;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CryptService cryptService;

    @Override
    public Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario,String role) {
        Usuario usuarioMapeado = mapearUsuario(usuario,role);
        try{
            return usuarioRepository.save(usuarioMapeado);
        }catch(Exception ex){
            throw new SalvarEntidadeException("Error ao criar novo usuário", ex);
        }
    }

    @Override
    public Usuario encontrarUsuarioPorUserName(String username) {
        return usuarioRepository.findByUserName(username).orElseGet(Usuario::new);
    }

    @Override
    public Usuario criarNovoGestor(Usuario criador,Usuario usuario, String role) {
        Usuario usuarioMapeado = mapearGestor(criador,usuario,role);
        try{
            return usuarioRepository.save(usuarioMapeado);
        }catch(Exception ex){
            throw new SalvarEntidadeException("Error ao criar novo usuário", ex);
        }
    }

    @Override
    public Usuario criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider) {
        try {
            Role userRole = roleRepository.findByNome("ROLE_CLIENTE");
            Usuario usuario = Usuario.builder()
                    .ativo(true)
                    .userName(email)
                    .nome(nome)
                    .authProvider(provider)
                    .dataCriacao(new Date())
                    .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                    .build();
            return usuarioRepository.save(usuario);
        }catch(Exception ex){
            throw new SalvarEntidadeException("Erro ao criar Usuário OAuth2",ex);
        }
    }

    private Usuario mapearUsuario(Usuario usuario,String role){
        try{
            Role userRole = roleRepository.findByNome(role);
            return Usuario.builder()
                    .password(bCryptPasswordEncoder.encode(usuario.getPassword()))
                    .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                    .ativo(true)
                    .cpf(usuario.getCpf())
                    .senhaResetada(false)
                    .genero(usuario.getGenero())
                    .nome(usuario.getNome())
                    .userName(usuario.getUserName())
                    .dataCriacao(new Date())
                    .endereco(usuario.getEndereco())
                    .dataCriacao(new Date())
                    .authProvider(AuthenticationProvider.LOCAL)
                    .build();
        }catch (Exception ex){
            throw new MappearEntidadeException("Erro ao mappear entidade Usuário.",ex);
        }

    }

    @Override
    public boolean gerarSenhaUsuario(String username, RedirectAttributes redirectAttributes){
        Usuario usuario = encontrarUsuarioPorUserName(username);
        if(Strings.isNullOrEmpty(usuario.getUserName())){
            redirectAttributes.addFlashAttribute("mensagemError", "Email não encontrado");
            return false;
        }else{
            String novaSenha = cryptService.gerar();
            emailService.sendMail(usuario.getUserName(),novaSenha);
            resetarSenha(usuario,novaSenha,true);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Um email foi enviado para "+username+" com a senha temporária.");
            return true;
        }
    }

    @Override
    public void usuarioMudaSenhaQuandoResetada(String user,String senhaAlterada, RedirectAttributes redirectAttribute){
        Usuario usuario = encontrarUsuarioPorUserName(user);
        resetarSenha(usuario,senhaAlterada,false);
        redirectAttribute.addFlashAttribute("mensagemSucesso", "Senha alterada, efetue login com a nova senha.");
    }

    @Override
    public void editarPerfil(UsuarioEditarPerfil usuarioEditarPerfil){
        Usuario usuario = encontrarUsuarioPorUserName(usuarioEditarPerfil.getUserName());
        usuario.setGenero(usuarioEditarPerfil.getGenero());
        usuario.setCpf(usuarioEditarPerfil.getCpf());
        usuario.setEndereco(usuarioEditarPerfil.getEndereco());
        usuario.setPerfilAtualizado(true);
        usuario.setFormaPagamento(usuarioEditarPerfil.getFormaPagamento());
        usuarioRepository.save(usuario);
    }



    private Usuario mapearGestor(Usuario criador,Usuario usuario,String role){
        try{
            Role userRole = roleRepository.findByNome(role);
            return Usuario.builder()
                    .password(bCryptPasswordEncoder.encode(usuario.getPassword()))
                    .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                    .ativo(true)
                    .cpf(usuario.getCpf())
                    .senhaResetada(false)
                    .genero(usuario.getGenero())
                    .nome(usuario.getNome())
                    .userName(usuario.getUserName())
                    .dataCriacao(new Date())
                    .endereco(usuario.getEndereco())
                    .dataCriacao(new Date())
                    .authProvider(AuthenticationProvider.LOCAL)
                    .estacionamentos(criador.getEstacionamentos())
                    .build();
        }catch (Exception ex){
            throw new MappearEntidadeException("Erro ao mappear entidade Usuário.",ex);
        }

    }

    private void resetarSenha(Usuario usuario,String novaSenha,boolean isResetada){
        usuario.setSenhaResetada(isResetada);
        usuario.setPassword(bCryptPasswordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }



}
