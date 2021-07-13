package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.RecuperarSenhaForm;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import br.com.autoparking.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/cadastrar")
    public String paginaCadastro(Model model){
       model.addAttribute("usuario", new Usuario());
       model.addAttribute("estado", estadoService.listarTodosEstados());
       model.addAttribute("genero", Genero.values());
       return "cadastrar";
    }

    @PostMapping(value="/cadastrar")
    public String salvarCadastro(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("estado", estadoService.listarTodosEstados());
            model.addAttribute("genero", Genero.values());
            return "cadastrar";
        }
        usuarioService.criarNovoUsuarioFormularioRegistro(usuario,"ROLE_ADMIN");
        redirectAttributes.addFlashAttribute("mensagemSucesso","Usuário cadastrado com sucesso!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/recuperar")
    public String recuperarSenha(Model model){
        model.addAttribute("recuperaSenhaForm", new RecuperarSenhaForm());
        return "recuperar-senha";
    }

    @PostMapping("/recuperar")
    public String salvarRecuperarSenha(@Valid RecuperarSenhaForm recuperarSenhaForm,BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return recuperarSenha(model);
        }
        if(usuarioService.gerarSenhaUsuario(recuperarSenhaForm.getEmail(),redirectAttributes)){
            return "redirect:/login";
        }
       return "redirect:/recuperar";
    }

}
