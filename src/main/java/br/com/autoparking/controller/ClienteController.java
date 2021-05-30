package br.com.autoparking.controller;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Cor;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.CarroService;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/home")
public class ClienteController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarroService carroService;

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

    @GetMapping("/carros")
    public String paginaListaCarros(Model model,HttpSession session){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        List<Carro> carros = carroService.listaCarrosPorUsuario(usuario);
        model.addAttribute("carros", carros);
        model.addAttribute("carro",new Carro());
        model.addAttribute("cor", Cor.values());
        return "/cliente/carros";
    }

    @PostMapping("/carros/novo")
    public String adicionarCarro(Carro carro,Model model,HttpSession session){
        Usuario userSession = (Usuario) session.getAttribute("user");
        carro.setUsuario(userSession);
        carroService.salvarCarro(carro);
        model.addAttribute("mensagemSucesso","Veiculo adicionado.");
        return paginaListaCarros(model,session);
    }
}
