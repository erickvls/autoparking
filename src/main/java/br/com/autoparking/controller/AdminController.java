package br.com.autoparking.controller;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Servico;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;
import br.com.autoparking.repository.UsuarioRepository;
import br.com.autoparking.security.UsuarioDetails;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.ServicoService;
import br.com.autoparking.service.UsuarioService;
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

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin")
    public String homeAdmin(){
        return "/admin/admin";
    }

    @GetMapping("/admin/estacionamentos/novo")
    public String cadastrarEstacionamento(Model model, Authentication authentication,RedirectAttributes redirectAttributes, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        if(usuario.getEstacionamentos().size()>0){
            redirectAttributes.addFlashAttribute("mensagemError","Você só pode criar 1 estacionamento.");
            return "redirect:/admin";
        }
        model.addAttribute("estacionamentoForm", new EstacionamentoForm());
        model.addAttribute("listaEstados",estadoService.listarTodosEstados());
        return "admin/estacionamento/novo";
    }

    @GetMapping("/admin/estacionamentos")
    public String listarEstacionamento(Model model, Authentication authentication, Servico servico){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        if(usuario.getEstacionamentos().size()<1){
            model.addAttribute("mensagemError","Você ainda não possui nenhum estacionamento cadastrado");
        }
        model.addAttribute("estacionamentos", usuario.getEstacionamentos());
        return "admin/estacionamento/listar";
    }


    @PostMapping ("/admin/estacionamentos/novo")
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

    @PostMapping ("/admin/estacionamento/servico")
    public String salvarServico(@Valid Servico servico, BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes,
                                       Model model){
        servicoService.salvar(servico);
        redirectAttributes.addFlashAttribute("mensagemSucesso","Serviço adicionao com sucesso!");
        return "redirect:/admin/estacionamentos";
    }
}
