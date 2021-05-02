package br.com.autoparking.model.enums;

public enum StatusOrder {
    EM_ABERTO("Em aberto"),
    ANDAMENTO("Andamento"),
    FECHADO("Fechado");

    private String status;

    StatusOrder(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return status;
    }
}

