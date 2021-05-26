package br.com.autoparking.service.exception;

public class SalvarEntidadeException extends RuntimeException {
    public SalvarEntidadeException(String message, Exception e) {
        super(message, e);
    }
}
