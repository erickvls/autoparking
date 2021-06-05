package br.com.autoparking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamentoDTO {
    private int id;
    private String nome;
    private int capacidade;
    private String eixoX;
    private String eixoY;
    private String horarioAbre;
    private String horarioFecha;
    private Map<String,String> servicos;
}
