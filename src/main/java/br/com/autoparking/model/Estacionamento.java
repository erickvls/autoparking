package br.com.autoparking.model;

import lombok.*;

import javax.persistence.*;
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
    private String nome;
    private String endereco;
    private String eixoX;
    private String eixoY;
    private String telefone;
    private int quantidadeVagas;
    private BigDecimal precoHora;
    private BigDecimal precoFixo;

    @OneToMany(mappedBy="estacionamento")
    private Set<Vaga> vaga;

    @OneToMany(mappedBy="estacionamento")
    private Set<Servico> servicos;

    @OneToMany(mappedBy="estacionamento")
    private Set<Order> order;

    @ManyToOne
    @JoinColumn(name="dono_id", nullable=false)
    private Dono dono;

}
