package br.com.autoparking.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PaginaErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("titulo","Página não encontrada");
                model.addAttribute("codigo","404");
                return "error";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("titulo","Error no servidor");
                model.addAttribute("codigo","500");
                model.addAttribute("mensagem","Entre em contato com o administrador do sistema.");
                return "error";
            }else if(statusCode == HttpStatus.FORBIDDEN.value()){
                model.addAttribute("titulo","Acesso negado");
                model.addAttribute("codigo","403");
                model.addAttribute("mensagem","Você não tem acesso para acessar essa página.");
                return "error";
            }
        }
        return "error";
    }
}
