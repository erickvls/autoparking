package br.com.autoparking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaturaDTO {
    private long id;
    private int dia;
    private int faturaPorDia;
    private BigDecimal total;
}
