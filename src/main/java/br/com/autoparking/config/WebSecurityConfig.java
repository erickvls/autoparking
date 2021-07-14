package br.com.autoparking.config;

import br.com.autoparking.security.handler.FormLoginSuccessHandler;
import br.com.autoparking.security.oauth.CustomOAuth2UsuarioService;
import br.com.autoparking.security.handler.OAuth2LoginSuccessHandler;
import br.com.autoparking.security.impl.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
       return new UsuarioDetailsServiceImpl();
    }

    @Autowired
    private CustomOAuth2UsuarioService oAuth2UsuarioService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private FormLoginSuccessHandler formLoginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/cadastrar").permitAll()
                .antMatchers("/recuperar").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin","/admin/pesquisar/**","/admin/estacionamento/reservar","/admin/orders","/admin/estacionamentos","/admin/fatura/**")
                    .hasAnyAuthority("ROLE_GESTOR","ROLE_ADMIN")
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/home/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/").permitAll()
                .antMatchers("/resources/*","/assets/**","/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                    .successHandler(formLoginSuccessHandler)
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint().userService(oAuth2UsuarioService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout().permitAll();
            http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/img/**")
                .and()
                .ignoring()
                .antMatchers("/vendor/**")
                .and()
                .ignoring()
                .antMatchers("/js/**")
                .and()
                .ignoring()
                .antMatchers("/css/**")
                .and()
                .ignoring()
                .antMatchers("/static/**");


    }


}
