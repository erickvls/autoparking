package br.com.autoparking.service.impl;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.repository.RoleRepository;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.service.UsuarioService;
import br.com.autoparking.validations.UsuarioExisteValid;
import br.com.autoparking.validations.UsuarioExisteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario criarNovoUsuarioFormularioRegistro(Usuario usuario) {
        Usuario usuarioMapeado = mapearUsuario(usuario);
        return usuarioRepository.save(usuarioMapeado);
    }

    @Override
    public Usuario encontrarUsuarioPorUserName(String username) {
        return usuarioRepository.findByUserName(username).orElseGet(Usuario::new);
    }

    @Override
    public Usuario criarNovoUsuarioDepoisOAuthSucesso(String email, String nome, AuthenticationProvider provider) {
        Role userRole = roleRepository.findByNome("ROLE_CLIENTE");
        Usuario usuario =  Usuario.builder()
                .ativo(true)
                .userName(email)
                .nome(nome)
                .authProvider(provider)
                .dataCriacao(new Date())
                .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                .build();
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario atualizarUsuarioDepoisOAuthSucesso(Usuario usuario,AuthenticationProvider google) {
        usuario.setAuthProvider(google);
        return usuarioRepository.save(usuario);
    }

    private Usuario mapearUsuario(Usuario usuario){
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
                .authProvider(AuthenticationProvider.LOCAL)
                .build();
    }
}
