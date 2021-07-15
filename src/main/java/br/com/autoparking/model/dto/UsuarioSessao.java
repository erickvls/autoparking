package br.com.autoparking.model.dto;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.model.enums.Genero;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class UsuarioSessao {

    private long id;
    private String userName;
    private String password;
    private boolean ativo;
    private String nome;
    private String cpf;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;
    private Date dataCriacao;
    private Set<Role> roles = new HashSet<>();
    private Set<Estacionamento> estacionamento;
    private Set<Order> orders;
    private Set<FormaPagamento> formaPagamento;
    private Set<Carro> carros;
    private Endereco endereco;
}
