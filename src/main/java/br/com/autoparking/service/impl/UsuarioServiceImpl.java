package br.com.autoparking.service.impl;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.repository.RoleRepository;
import br.com.autoparking.repository.UsuarioRepository;
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

    @Override
    public Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario) {
        Usuario usuarioMapeado = mapearUsuario(usuario);
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

    private Usuario mapearUsuario(Usuario usuario){
        try{
            Role userRole = roleRepository.findByNome("ROLE_ADMIN");
            return Usuario.builder()
                    .password(bCryptPasswordEncoder.encode(usuario.getPassword()))
                    .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                    .ativo(true)
                    .cpf(usuario.getCpf())
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
    public boolean resetarSenhaUsuario(String username, RedirectAttributes redirectAttributes){
        String usuario = usuarioRepository.findByUserName(username).map(Usuario::getUserName).stream().collect(Collectors.joining());
        if(Strings.isNullOrEmpty(usuario)){
            redirectAttributes.addFlashAttribute("mensagemError", "Email não encontrado");
            return false;
        }else{
            emailService.sendMail(usuario);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Um email foi enviado para "+username+" com a senha temporária.");
            return true;
        }
    }
}
