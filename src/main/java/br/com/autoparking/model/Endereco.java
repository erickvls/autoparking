package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "O campo estado não pode está vazio")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado")
    private Estado estado;
    @NotEmpty(message = "O campo cidade não pode está vazio")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cidade")
    private Cidade cidade;
    @NotEmpty(message = "O campo rua não pode está vazio")
    private String rua;
    @NotEmpty(message = "O campo bairro não pode está vazio")
    private String bairro;
    private String numero;
}
