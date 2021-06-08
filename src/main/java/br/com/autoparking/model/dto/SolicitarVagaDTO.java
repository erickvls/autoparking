package br.com.autoparking.model.dto;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarVagaDTO {
    private Estacionamento estacionamento;
    private Usuario cliente;
    private Carro veiculo;
    private String dataPrevistaEntrada;
    private String dataPrevistaSaida;
}
