package br.com.autoparking.service.exception;

public class MappearEntidadeException  extends RuntimeException{
    public MappearEntidadeException(String message, Exception e) {
        super(message, e);
    }
}
