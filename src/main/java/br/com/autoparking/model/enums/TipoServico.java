package br.com.autoparking.model.enums;

public enum TipoServico {
    RESERVA("Valor da reserva"),
    HORA("Valor da hora"),
    OUTRO("Outro");

    private String tipoServico;

    TipoServico(String tipoServico){
        this.tipoServico = tipoServico;
    }


    public String getTipoServico(){
        return tipoServico;
    }

    @Override
    public String toString(){
        return tipoServico;
    }
}
