package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/home")
public class ClienteController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String homeCliente(Model model){
        model.addAttribute("estacionamentos",estacionamentoService.listar());
        return "/cliente/home";
    }

    @GetMapping("/perfil")
    public String paginaEditarPerfil(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        model.addAttribute("usuario", usuario);
        model.addAttribute("estado", estadoService.listarTodosEstados());
        model.addAttribute("genero", Genero.values());
        return "/cliente/meuperfil";
    }

    @PostMapping("/perfil")
    public String salvarEditarPerfil(@Valid UsuarioEditarPerfil usuario,Model model){
        usuarioService.editarPerfil(usuario);
        model.addAttribute("mensagemSucesso", "Perfil atualizado com sucesso.");
        return homeCliente(model);
    }
}
