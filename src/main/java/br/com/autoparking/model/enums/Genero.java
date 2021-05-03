package br.com.autoparking.model.enums;

public enum Genero {
    M("Masculino"),
    F("Feminino"),
    O("Outro");

    private String nome;

    Genero(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}
