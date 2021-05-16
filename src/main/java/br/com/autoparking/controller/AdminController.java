package br.com.autoparking.controller;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller

public class AdminController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping("/admin")
    public String homeAdmin(Model model){
        model.addAttribute("estacionamento", new Estacionamento());
        model.addAttribute("listaEstados",estadoService.listarTodosEstados());
        return "/admin/admin";
    }

    @PostMapping ("/admin/estacionamento")
    public String cadastrarEstacionamento(@Valid Estacionamento estacionamento, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/admin/admin";
        }
        //usuarioService.criarNovoUsuarioFormularioRegistro(usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso","Usuário cadastrado com sucesso!");
        return "redirect:/login";
    }
}
