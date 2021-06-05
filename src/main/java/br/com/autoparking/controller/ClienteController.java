package br.com.autoparking.controller;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.Cor;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.model.enums.MetodoPagamento;
import br.com.autoparking.service.CarroService;
import br.com.autoparking.service.EstacionamentoService;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        Usuario usuarioSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(usuarioSession.getUserName());
        model.addAttribute("usuario", usuario);
        model.addAttribute("estado", estadoService.listarTodosEstados());
        model.addAttribute("genero", Genero.values());
        model.addAttribute("tipoPagamento", MetodoPagamento.CARTAO);
        return "/cliente/meuperfil";
    }

    @PostMapping("/perfil")
    public String salvarEditarPerfil(@Valid UsuarioEditarPerfil usuario, BindingResult bindingResult,Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            model.addAttribute("mensagemError",bindingResult.getFieldError().getDefaultMessage());
            return paginaEditarPerfil(model,session);
        }
        usuarioService.editarPerfil(usuario);
        model.addAttribute("mensagemSucesso", "Perfil atualizado com sucesso.");
        return homeCliente(model);
    }

    @GetMapping("/veiculos")
    public String paginaListaVeiculos(Model model,HttpSession session){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        List<Carro> carros = carroService.listaCarrosPorUsuario(usuario);
        model.addAttribute("carros", carros);
        model.addAttribute("carro",new Carro());
        model.addAttribute("cor", Cor.values());
        return "/cliente/carros";
    }

    @PostMapping("/veiculos")
    public String adicionarVeiculo(Carro carro,Model model,HttpSession session){
        Usuario userSession = (Usuario) session.getAttribute("user");
        carro.setUsuario(userSession);
        carroService.salvarCarro(carro);
        model.addAttribute("mensagemSucesso","Veiculo adicionado.");
        return paginaListaVeiculos(model,session);
    }


    @GetMapping("/veiculos/{id}")
    public String paginaEditarVeiculos(@PathVariable Long id, HttpSession session,Model model,RedirectAttributes redirectAttributes){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Carro carro = carroService.encontrarVeiculoPeloUsuarioEIdVeiculo(userSession,id);
        if(Objects.isNull(carro)){
            redirectAttributes.addFlashAttribute("mensagemError", "Não foram encontrados veículos para seu perfil");
            return "redirect:/veiculos";
        }
        model.addAttribute("carro", carro);
        model.addAttribute("cor",Cor.values());
        return "/cliente/editar-veiculo";
    }

    @PostMapping("/veiculos/editar")
    public String editarVeiculos(Carro carro, HttpSession session, RedirectAttributes redirectAttributes,Model model){
        carroService.salvarCarro(carro);
        model.addAttribute("mensagemSucesso", "Veículo editado com sucesso");
        return paginaListaVeiculos(model,session);
    }

    @PostMapping("/veiculos/excluir/")
    public String excluirVeiculos(@ModelAttribute("carro") Carro carro,HttpSession session,Model model){
        Usuario userSession = (Usuario) session.getAttribute("user");
        carroService.deletarCarroLogicamente(carro,userSession);
        return paginaListaVeiculos(model,session);
    }
}
