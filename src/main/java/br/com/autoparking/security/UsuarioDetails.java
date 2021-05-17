package br.com.autoparking.security;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class UsuarioDetails implements UserDetails {

    private Usuario usuario;

    public UsuarioDetails(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles =  usuario.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role:roles){
            authorities.add(new SimpleGrantedAuthority(role.getNome()));
        }
        return  authorities;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo();
    }
}
