package br.com.autoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @GetMapping("/home")
    public String homeCliente(){
        return "/cliente/home";
    }
}