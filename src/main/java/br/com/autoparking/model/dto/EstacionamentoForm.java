package br.com.autoparking.model.dto;

import br.com.autoparking.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamentoForm {

    @NotEmpty(message = "Preencha o nome do estacionamento.")
    private String nome;
    @NotEmpty(message = "Digite a latitude.")
    private String eixoX;
    @NotEmpty(message = "Digite a longitude.")
    private String eixoY;
    @NotEmpty(message = "Digite o horário de abertura")
    private String horarioAbre;
    @NotEmpty(message = "Digite o horário que fecha.")
    private String horarioFecha;
    private String telefone;
    @Min(value = 1,message = "A quantidade de vagas tem que ser no mínimo 1.")
    private int quantidadeVagas;
    @NotNull
    private Estado estado;
    @NotEmpty(message = "O campo cidade não pode está vazio")
    private String cidade;
    @Size(min = 5,max = 20,message = "O campo deve conter entre 5 e 20 caracteres.")
    private String rua;
    @Size(min = 4,max = 10,message = "O campo deve conter entre 4 e 10 caracteres.")
    private String bairro;
}
