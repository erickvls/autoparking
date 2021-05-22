package br.com.autoparking.service.exception;

public class FalhaAoSalvarException extends RuntimeException {
    public FalhaAoSalvarException(String message, Exception e) {
        super(message, e);
    }
}
