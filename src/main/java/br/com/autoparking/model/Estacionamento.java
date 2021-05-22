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

    @OneToMany(mappedBy="estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vaga> vaga;

    @OneToMany(mappedBy="estacionamento",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Servico> servicos;

    @OneToMany(mappedBy="estacionamento",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco")
    @NotNull
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name="usuario")
    private Usuario usuario;

}
