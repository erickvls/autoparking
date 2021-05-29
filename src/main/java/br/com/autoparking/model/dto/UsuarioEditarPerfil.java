package br.com.autoparking.model.dto;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Endereco;
import br.com.autoparking.model.FormaPagamento;
import br.com.autoparking.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEditarPerfil {

    @NotEmpty(message = "O campo nome não pode estar em branco.")
    private String nome;
    @CPF(message = "CPF inserido não é válido")
    private String cpf;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String telefone;

    private Endereco endereco;

    private List<Carro> carros;

    private List<FormaPagamento> formaPagamentos;
}
