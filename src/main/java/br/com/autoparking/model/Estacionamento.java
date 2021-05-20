package br.com.autoparking.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "estacionamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String eixoX;
    @NotEmpty
    private String eixoY;
    private String telefone;
    @NotEmpty
    private String horarioAbre;
    @NotEmpty
    private String horarioFecha;
    @Min(1)
    private int quantidadeVagas;
    private BigDecimal precoHora;
    private BigDecimal precoFixo;

    @OneToMany(mappedBy="estacionamento")
    private Set<Vaga> vaga;

    @OneToMany(mappedBy="estacionamento")
    private Set<Servico> servicos;

    @OneToMany(mappedBy="estacionamento")
    private Set<Order> order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco")
    @NotNull
    private Endereco endereco;

    @OneToMany(mappedBy="estacionamento")
    private Set<Usuario> usuario;

}
