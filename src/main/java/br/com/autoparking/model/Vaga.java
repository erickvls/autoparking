package br.com.autoparking.model;

import br.com.autoparking.model.enums.StatusVaga;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private StatusVaga status;

    @ManyToOne
    @JoinColumn(name="estacionamento_id", nullable=false)
    private Estacionamento estacionamento;
}
