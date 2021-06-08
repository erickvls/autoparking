package br.com.autoparking.model;

import lombok.*;

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
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="vaga_id", nullable=false)
    private Vaga vaga;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne(cascade=CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="carro_id", nullable=false)
    private Carro carro;
}
