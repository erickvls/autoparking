package br.com.autoparking.controller.cliente;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.Cor;
import br.com.autoparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/home")
public class VeiculosController {

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


    @GetMapping("/veiculos")
    public String paginaListaVeiculos(Model model, HttpSession session){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        List<Carro> carros = carroService.listaCarrosPorUsuario(usuario);
        model.addAttribute("carros", carros);
        model.addAttribute("carro",new Carro());
        model.addAttribute("cor", Cor.values());
        return "cliente/carros";
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
    public String paginaEditarVeiculos(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Carro carro = carroService.encontrarVeiculoPeloUsuarioEIdVeiculo(userSession,id);
        if(Objects.isNull(carro)){
            redirectAttributes.addFlashAttribute("mensagemError", "Não foram encontrados veículos para seu perfil");
            return "redirect:/veiculos";
        }
        model.addAttribute("carro", carro);
        model.addAttribute("cor",Cor.values());
        return "cliente/editar-veiculo";
    }

    @PostMapping("/veiculos/editar")
    public String editarVeiculos(Carro carro, HttpSession session, RedirectAttributes redirectAttributes,Model model){
        carroService.salvarCarro(carro);
        model.addAttribute("mensagemSucesso", "Veículo editado com sucesso");
        return paginaListaVeiculos(model,session);
    }

    @PostMapping("/veiculos/excluir/")
    public String excluirVeiculos(@ModelAttribute("carro") Carro carro, HttpSession session, Model model){
        Usuario userSession = (Usuario) session.getAttribute("user");
        carroService.deletarCarroLogicamente(carro,userSession);
        return paginaListaVeiculos(model,session);
    }
}
