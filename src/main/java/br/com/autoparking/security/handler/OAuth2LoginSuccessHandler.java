package br.com.autoparking.security.handler;

import br.com.autoparking.model.Role;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.security.oauth.CustomOAuth2Usuario;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FormLoginSuccessHandler formLoginSuccessHandler;

    @Autowired
    private FormLoginErrorHandler formLoginErrorHandler;

    private String url;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2Usuario oAuth2Usuario = (CustomOAuth2Usuario) authentication.getPrincipal();
        String email = oAuth2Usuario.getEmail();
        String nome = oAuth2Usuario.getFullName();

        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(email);

        if(Objects.isNull(usuario.getUserName())){
            usuario = usuarioService.criarNovoUsuarioDepoisOAuthSucesso(email,nome, AuthenticationProvider.GOOGLE);
            if(!usuario.isPerfilAtualizado()){
                url = "/home/perfil";
            }else{
                url = "/home";
            }
            request.getSession().setAttribute("user", usuario);
            redirectStrategy.sendRedirect(request, response, url);

        }else if(usuario.getRoles().stream().anyMatch(v->v.getNome().equals("ROLE_ADMIN"))) {
            formLoginErrorHandler.onAuthenticationFailure(request,response, new BadCredentialsException("Acesso não permitido"));
        }else if(usuario.getRoles().stream().anyMatch(v->v.getNome().equals("ROLE_USER"))){
            if(!usuario.isPerfilAtualizado()){
                url = "/home/perfil";
            }else{
                url = "/home";
            }
            request.getSession().setAttribute("user", usuario);
            redirectStrategy.sendRedirect(request, response, url);
        }
    }
}