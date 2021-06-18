package br.com.autoparking.model;

import br.com.autoparking.model.enums.StatusOrder;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="estacionamento_id", nullable=false)
    @JsonIgnore
    private Estacionamento estacionamento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fatura_id")
    private Fatura fatura;

    private LocalDateTime dataPrevistaEntrada;
    private LocalDateTime dataPrevistaSaida;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private LocalDateTime duracao;
    private LocalDateTime dataOrder;
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="usuario", nullable=false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy="order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacao;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vagaHorario_id")
    private VagaHorario vagaHorario;
}
