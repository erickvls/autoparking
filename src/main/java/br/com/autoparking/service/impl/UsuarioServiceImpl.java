package br.com.autoparking.service.impl;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.repository.RoleRepository;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

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
    public void salvarUsuario(Usuario usuario) {
        Usuario usuarioMapeado = mapearUsuario(usuario);
        usuarioRepository.save(usuarioMapeado);
    }

    private Usuario mapearUsuario(Usuario usuario){
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        Role userRole = roleRepository.findByNome("ADMIN");
        usuario.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        usuario.setAtivo(true);
        usuario.setCpf(usuario.getCpf());
        usuario.setGenero(usuario.getGenero());
        usuario.setNome(usuario.getNome());
        usuario.setUserName(usuario.getUserName());
        return usuario;
    }
}
