package br.com.autoparking.model.dto;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.enums.TipoServico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoFormDTO {
    private String descricao;

    private String valor;

    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    private Estacionamento estacionamento;
}
