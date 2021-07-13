package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class GestorController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("${autoparking.url.admin}/gestor")
    public String cadastrarGestor(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("estado", estadoService.listarTodosEstados());
        model.addAttribute("genero", Genero.values());
        return "/admin/gestores/novo";
    }

    @PostMapping("${autoparking.url.admin}/gestor")
    public String salvarGestor(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            model.addAttribute("estado", estadoService.listarTodosEstados());
            model.addAttribute("genero", Genero.values());
            return "/admin/gestores/novo";
        }

        Usuario criador = (Usuario) session.getAttribute("user");
        usuarioService.criarNovoGestor(criador,usuario,"ROLE_GESTOR");
        redirectAttributes.addFlashAttribute("mensagemSucesso","Gestor cadastrado com sucesso!");
        return "redirect:/admin/";
    }
}
