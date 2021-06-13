package br.com.autoparking.model.dto;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Order;
import br.com.autoparking.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaExibirDTO {
    Usuario usuario;
    private long id;
    private LocalDateTime dataPrevistaEntrada;
    private LocalDateTime dataPrevistaSaída;
    private List<CarroDTO> veiculos;
    private CarroDTO veiculoSelecionado;
}
