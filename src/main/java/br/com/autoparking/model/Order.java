package br.com.autoparking.model;

import br.com.autoparking.model.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_est")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="estacionamento_id", nullable=false)
    private Estacionamento estacionamento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fatura_id")
    private Fatura fatura;

    private LocalDate dataPrevista;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private LocalDate duracao;
    private LocalDate dataOrder;
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name="usuario", nullable=false)
    private Usuario usuario;

    @OneToMany(mappedBy="order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacao;
}
