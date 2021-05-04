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
    public void criarNovoUsuarioFormularioRegistro(Usuario usuario) {
        Usuario usuarioMapeado = mapearUsuario(usuario);
        usuarioRepository.save(usuarioMapeado);
    }

    @Override
    public boolean usuariosIsAvailable(String username) {
        Optional<Usuario> usuario = usuarioRepository.findByUserName(username);
        return usuario.map(Usuario::getId).isEmpty();
    }

    @Override
    public void criarNovoUsuarioDepoisOAuthSucesso( String email, String nome, AuthenticationProvider provider) {
        boolean usuarioIsAvailable = usuariosIsAvailable(email);
        if(usuarioIsAvailable) {
            Role userRole = roleRepository.findByNome("CLIENTE");
            Usuario usuario =  Usuario.builder()
                    .ativo(true)
                    .userName(email)
                    .nome(nome)
                    .authProvider(provider)
                    .dataCriacao(new Date())
                    .roles(new HashSet<Role>(Collections.singletonList(userRole)))
                    .build();
            usuarioRepository.save(usuario);
        }
    }

    private Usuario mapearUsuario(Usuario usuario){
        Role userRole = roleRepository.findByNome("ADMIN");
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
