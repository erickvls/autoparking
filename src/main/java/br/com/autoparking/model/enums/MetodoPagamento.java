package br.com.autoparking.model.enums;

public enum MetodoPagamento {
    CARTAO("Cartão de Crédito/Débito"),
    DINHEIRO("Dinheiro");

    private String valor;

    MetodoPagamento(String valor){
        this.valor = valor;
    }

    String getValor(){
        return this.valor;
    }
}
