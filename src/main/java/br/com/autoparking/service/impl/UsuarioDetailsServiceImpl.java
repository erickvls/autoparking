package br.com.autoparking.service.impl;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.service.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUserName(username);
        return usuario.map(user -> new UsuarioDetails(user))
                .orElseThrow(()-> new UsernameNotFoundException(username+ "não existe."));
    }
}
