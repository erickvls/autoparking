package br.com.autoparking.service.exception;

public class EnviarEmailException extends RuntimeException {
    public EnviarEmailException(String message, Exception e) {
        super(message, e);
    }
}
