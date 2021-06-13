package br.com.autoparking.model.enums;

public enum TipoServico {
    RESERVA("Valor para reservar"),
    PENALIDADE("Penalidade de reserva não comparecida"),
    HORA("Valor da hora"),
    FRACAO("Valor fração da hora"),
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
