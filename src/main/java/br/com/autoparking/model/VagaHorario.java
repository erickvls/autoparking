package br.com.autoparking.model;

import br.com.autoparking.model.enums.StatusVaga;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaga_horario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VagaHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="vaga_id", nullable=false)
    private Vaga vaga;

    @Enumerated(EnumType.STRING)
    private StatusVaga statusVaga;

    @OneToOne(mappedBy = "vagaHorario", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Order order;

    private LocalDateTime horaChegada;
    private LocalDateTime horaSaida;
}
