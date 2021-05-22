package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "servico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    private BigDecimal valor;

    @OneToMany(mappedBy="servico",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FaturaServicos> faturaServicos;

    @ManyToOne
    @JoinColumn(name="estacionamento_id", nullable=false)
    private Estacionamento estacionamento;
}
