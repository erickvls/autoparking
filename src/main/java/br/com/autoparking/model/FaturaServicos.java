package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fatura_servicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaturaServicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="fatura_id", nullable=false)
    private Fatura fatura;

    @ManyToOne
    @JoinColumn(name="servico_id", nullable=false)
    private Servico servico;
}
