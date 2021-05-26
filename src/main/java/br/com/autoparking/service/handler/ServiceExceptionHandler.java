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
        //ErroPadrao error = new ErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        return "ERROR - fale com o administrador";
    }

}
