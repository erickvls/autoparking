package br.com.autoparking.controller;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.security.UsuarioDetails;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

public class AdminController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstacionamentoService estacionamentoService;

    @GetMapping("/admin")
    public String homeAdmin(){
        return "/admin/admin";
    }

    @GetMapping("/admin/estacionamento")
    public String cadastrarEstacionamento(Model model){
        model.addAttribute("estacionamentoForm", new EstacionamentoForm());
        model.addAttribute("listaEstados",estadoService.listarTodosEstados());
        return "admin/estacionamento/novo";
    }
    @PostMapping ("/admin/estacionamento")
    public String salvarEstacionamento(@Valid EstacionamentoForm estacionamentoForm, BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes, HttpSession session,
                                        Model model){
        Usuario usuario = (Usuario) session.getAttribute("user");

        if(bindingResult.hasErrors()){
            model.addAttribute("listaEstados",estadoService.listarTodosEstados());
            return "admin/estacionamento/novo";
        }

        estacionamentoService.salvarEstacionamento(estacionamentoForm,usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso","Estacionamento cadastrado com sucesso!");
        return "redirect:/admin";
    }
}
