package br.com.autoparking.service.handler;

import br.com.autoparking.service.exception.SalvarEntidadeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(SalvarEntidadeException.class)
    String ObjectNotFound(SalvarEntidadeException e, HttpServletRequest request, Model model){
        return "ERROR - fale com o administrador";
    }

}
