package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class EstatisticaController {

    @GetMapping("${autoparking.url.admin}/estatisticas")
    public String estatistica(Model model, Authentication authentication, RedirectAttributes redirectAttributes, HttpSession session){
        return "admin/estatistica";
    }
}
