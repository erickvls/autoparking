package br.com.autoparking.controller;

import br.com.autoparking.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/cadastrar")
    public String paginaCadastro(Model model){
       model.addAttribute("usuario", new Usuario());
       return "cadastrar";
    }

    @PostMapping(value="/cadastrar")
    public String salvarCadastro(@Valid @ModelAttribute("usuario")  Usuario usuario, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "cadastrar";
        }
        Usuario usuario1 = usuario;
        return "redirect:/cadastrar";
    }

}
