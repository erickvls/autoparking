package br.com.autoparking.controller;

import br.com.autoparking.service.EstacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @GetMapping("/home")
    public String homeCliente(Model model){
        model.addAttribute("estacionamentos",estacionamentoService.listar());
        return "/cliente/home";
    }
}
