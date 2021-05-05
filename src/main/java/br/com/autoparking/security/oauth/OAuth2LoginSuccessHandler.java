package br.com.autoparking.security.oauth;

import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2Usuario oAuth2Usuario = (CustomOAuth2Usuario) authentication.getPrincipal();
        String email = oAuth2Usuario.getEmail();
        String nome = oAuth2Usuario.getFullName();
        System.out.println(oAuth2Usuario.getAttributes());
        boolean usuarioIsAvailable = usuarioService.usuariosIsAvailable(email);
        if(usuarioIsAvailable){
            usuarioService.criarNovoUsuarioDepoisOAuthSucesso(email,nome, AuthenticationProvider.GOOGLE);
        }else{
            usuarioService.atualizarUsuarioDepoisOAuthSucesso(email,nome,AuthenticationProvider.GOOGLE);
        }
        super.onAuthenticationSuccess(request,response,authentication);
    }
}