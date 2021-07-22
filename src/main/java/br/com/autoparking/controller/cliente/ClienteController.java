package br.com.autoparking.controller.cliente;

import br.com.autoparking.model.*;
import br.com.autoparking.model.dto.SolicitarVagaDTO;
import br.com.autoparking.model.dto.UsuarioEditarPerfil;
import br.com.autoparking.model.enums.Cor;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.model.enums.MetodoPagamento;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private VagaService vagaService;

    @Autowired
    private AlocacaoService alocacaoService;

    @Autowired
    private OrderService orderService;


    @GetMapping
    public String homeCliente(Model model){
        model.addAttribute("estacionamentos",estacionamentoService.listar());
        return "cliente/home";
    }

    @GetMapping("/estacionamento/visualizar/{estacionamento}")
    public String visualizarEstacionamento(@PathVariable Estacionamento estacionamento,Model model, HttpSession session,RedirectAttributes redirectAttributes){
        Usuario userSession = (Usuario) session.getAttribute("user");
        if(orderService.usuarioPossuiOrderAbertaOuAndamento(userSession,estacionamento)){
            redirectAttributes.addFlashAttribute("mensagemError","Você já possui uma ordem em aberto ou andamento nesse estacionamento. Para vi" +
                    "sualizar, acesse Perfil > Orders");
            return "redirect:/home";
        }
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        List<Carro> carros = carroService.listaCarrosAtivosPorUsuario(usuario);
        model.addAttribute("estacionamento", estacionamento);
        model.addAttribute("usuario",usuario);
        model.addAttribute("carros",carros);
        model.addAttribute("vagasTotais",estacionamento.getQuantidadeVagas());
        model.addAttribute("vagasDisponiveis",estacionamento.getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.LIVRE) || v.getStatus().equals(StatusVaga.RESERVADO)).count());
        model.addAttribute("vagasReservadas",estacionamento.getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.RESERVADO)).count());
        model.addAttribute("vagasOcupadas",estacionamento.getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.OCUPADO)).count());
        return "cliente/detalhes-estacionamento";
    }

    @PostMapping("/estacionamento/solicitar")
    public String solicitarVaga(@ModelAttribute("SolicitarVagaDTO") SolicitarVagaDTO solicitarVagaDTO,Model model,HttpSession session,RedirectAttributes redirectAttributes){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        solicitarVagaDTO.setCliente(usuario);
        return alocacaoService.alocarVagaPorEstacionamento(StatusVaga.LIVRE,
                solicitarVagaDTO.getEstacionamento(),
                usuario,
                redirectAttributes,
                solicitarVagaDTO.getDataPrevistaEntrada(),
                solicitarVagaDTO.getDataPrevistaSaida(),
                solicitarVagaDTO.getVeiculo());
    }

    @GetMapping("/orders")
    public String minhasOrders(Model model, HttpSession session,RedirectAttributes redirectAttributes){
        Usuario userSession = (Usuario) session.getAttribute("user");
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(userSession.getUserName());
        List<Order> orders = orderService.listarTodasOrdersDeUmUsuario(usuario);
        model.addAttribute("orders",orders);
        return "cliente/orders";
    }

}
