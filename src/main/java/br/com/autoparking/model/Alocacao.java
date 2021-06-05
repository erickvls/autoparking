package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "alocacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alocacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="vaga_id", nullable=false)
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="carro_id", nullable=false)
    private Carro carro;
}
