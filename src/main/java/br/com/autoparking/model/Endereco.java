package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "endereco")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado")
    private Estado estado;
    @NotEmpty(message = "O campo cidade não pode está vazio")
    private String cidade;
    @Size(min = 5,max = 40,message = "O campo deve conter entre 5 e 40 caracteres.")
    private String rua;
    @Size(min = 4,max = 10,message = "O campo deve conter entre 4 e 10 caracteres.")
    private String bairro;
    private String numero;
}
