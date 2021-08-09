package br.com.autoparking.controller.cliente;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.model.enums.MetodoPagamento;
import br.com.autoparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/home")
public class PerfilController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarroService carroService;

    @Autowired
    private VagaService vagaService;

    @Autowired
    private AlocacaoService alocacaoService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/perfil")
    public String paginaEditarPerfil(Model model, HttpSession session){
        Usuario usuarioSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(usuarioSession.getUserName());
        model.addAttribute("usuario", usuario);
        model.addAttribute("estado", estadoService.listarTodosEstados());
        model.addAttribute("genero", Genero.values());
        model.addAttribute("tipoPagamento", MetodoPagamento.CARTAO);
        return "cliente/meuperfil";
    }

    @PostMapping("/perfil")
    public String salvarEditarPerfil(@Valid UsuarioEditarPerfil usuario, BindingResult bindingResult, Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            model.addAttribute("mensagemError",bindingResult.getFieldError().getDefaultMessage());
            return paginaEditarPerfil(model,session);
        }
        usuarioService.editarPerfil(usuario);
        model.addAttribute("mensagemSucesso", "Perfil atualizado com sucesso.");
        model.addAttribute("estacionamentos",estacionamentoService.listar());
        return "cliente/home";
    }
}
