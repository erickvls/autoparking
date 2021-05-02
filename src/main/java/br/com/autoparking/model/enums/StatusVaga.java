package br.com.autoparking.model.enums;

public enum StatusVaga {
    OCUPADO("Ocupado"),
    RESERVADO("Reservado"),
    LIVRE("Livre");

    private String status;

    StatusVaga(String status){
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
