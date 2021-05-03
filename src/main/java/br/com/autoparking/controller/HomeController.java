package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    public UsuarioService usuarioService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/cadastrar")
    public String paginaCadastro(Model model){
       model.addAttribute("usuario", new Usuario());
       model.addAttribute("genero", Genero.values());
       return "cadastrar";
    }

    @PostMapping(value="/cadastrar")
    public String salvarCadastro(@Valid @ModelAttribute("usuario")  Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "cadastrar";
        }
        usuarioService.salvarUsuario(usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso","Usuário cadastrado com sucesso!");
        return "redirect:/login";
    }

}
