package br.com.autoparking.model.dto;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarVagaDTO {
    private Estacionamento estacionamento;
    private Usuario usuario;
    private Carro carro;
}
