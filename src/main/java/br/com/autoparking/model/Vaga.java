package br.com.autoparking.model;

import br.com.autoparking.model.enums.StatusVaga;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vaga")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String numero;

    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    @ManyToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="estacionamento_id",nullable = false, updatable = true, insertable = true)

    private Estacionamento estacionamento;

    @OneToMany(mappedBy="vaga",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacao;
}
