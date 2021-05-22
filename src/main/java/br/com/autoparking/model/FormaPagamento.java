package br.com.autoparking.model;

import br.com.autoparking.model.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "forma_pagamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    private String descricao;

    @ManyToOne
    @JoinColumn(name="usuario", nullable=false)
    private Usuario usuario;
}
