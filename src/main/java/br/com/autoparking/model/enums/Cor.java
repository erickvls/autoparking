package br.com.autoparking.model.enums;

public enum Cor {
    AMARELO("AMARELO"),
    PRETO("PRETO"),
    BRANCO("BRANCO"),
    AZUL("AZUL"),
    VERMELHO("VERMELHO"),
    CINZA("CINZA"),
    VERDE("VERDE"),
    ROSA("ROSA"),
    LARANJA("LARANJA");

    private final String descricao;

    private Cor(String descricao) {
        this.descricao = descricao;
    }
}
