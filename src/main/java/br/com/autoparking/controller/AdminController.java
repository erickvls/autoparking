package br.com.autoparking.controller;

import br.com.autoparking.model.*;
import br.com.autoparking.model.dto.CarroDTO;
import br.com.autoparking.model.dto.EstacionamentoForm;
import br.com.autoparking.model.dto.ReservaExibirDTO;
import br.com.autoparking.model.dto.ServicoFormDTO;
import br.com.autoparking.model.enums.StatusVaga;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private AlocacaoService alocacaoService;

    @Autowired
    private FaturaService faturaService;

    @GetMapping("${autoparking.url.admin}")
    public String homeAdmin(){
        return "/admin/admin";
    }

    /* --------------------- */
    /* INICIO ESTACIONAMENTO */
    /* --------------------- */

    @GetMapping("${autoparking.url.admin}/estacionamentos/novo")
    public String cadastrarEstacionamento(Model model, Authentication authentication,RedirectAttributes redirectAttributes, HttpSession session){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        if(usuario.getEstacionamentos().size()>0){
            redirectAttributes.addFlashAttribute("mensagemError","Você só pode criar 1 estacionamento.");
            return "redirect:/admin";
        }
        model.addAttribute("estacionamentoForm", new EstacionamentoForm());
        model.addAttribute("listaEstados",estadoService.listarTodosEstados());
        return "admin/estacionamento/novo";
    }

    @GetMapping("${autoparking.url.admin}/estacionamentos")
    public String listarEstacionamento(Model model, Authentication authentication, Servico servico,RedirectAttributes redirectAttribute){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        if(usuario.getEstacionamentos().size()<1){
            redirectAttribute.addFlashAttribute("mensagemError","Você ainda não possui nenhum estacionamento cadastrado");
            return "redirect:/admin";
        }
        Optional<Estacionamento> estacionamento = usuario.getEstacionamentos().stream().findFirst();
        model.addAttribute("vagasDisponiveis",estacionamento.get().getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.LIVRE) || v.getStatus().equals(StatusVaga.RESERVADO)).count());
        model.addAttribute("vagasReservadas",estacionamento.get().getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.RESERVADO)).count());
        model.addAttribute("vagasOcupadas",estacionamento.get().getVaga().stream().filter(v->v.getStatus().equals(StatusVaga.OCUPADO)).count());

        model.addAttribute("estacionamentos", usuario.getEstacionamentos());
        model.addAttribute("tipoServico", TipoServico.values());
        return "admin/estacionamento/listar";
    }


    @PostMapping ("${autoparking.url.admin}/estacionamentos/novo")
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

    @PostMapping ("${autoparking.url.admin}/estacionamento/servico")
    public String salvarServico(@Valid ServicoFormDTO servico,
                                RedirectAttributes redirectAttributes){
        return servicoService.salvar(servico,redirectAttributes);
    }

    /* ------------------- */
    /* FIM ESTACIONAMENTO */
    /* ------------------ */

    @GetMapping("${autoparking.url.admin}/atualizar")
    public String atualizarSenha(Model model,RedirectAttributes redirectAttribute,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        usuario.setPassword("");
        model.addAttribute("usuario", usuario);
        model.addAttribute("estado",estadoService.listarTodosEstados());
        return "admin/atualizar";
    }

    @PostMapping("${autoparking.url.admin}/atualizar")
    public String salvarNovaSenha(@RequestParam("userName") String userName,
                                  @RequestParam("password") String password,
                                  RedirectAttributes redirectAttribute,HttpSession session){

        usuarioService.usuarioMudaSenhaQuandoResetada(userName,password,redirectAttribute);
        return "redirect:/login";
    }

    /* ------------------- */
    /* ALOCAR VAGA */
    /* ------------------ */


    @PostMapping("${autoparking.url.admin}/pesquisar/usuario")
    public @ResponseBody ReservaExibirDTO pesquisarUsuario(@RequestParam("email") String email,
                           @RequestParam("estacionamento") Estacionamento estacionamento,
                           RedirectAttributes redirectAttribute, HttpSession session){

        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(email);
        if(Objects.isNull(usuario.getUserName())){
            return ReservaExibirDTO.builder().build();
        }
        Order order =  orderService.usuarioPossuiReserva(usuario,estacionamento);
        if(!Objects.isNull(order.getDataPrevistaEntrada())){
            Alocacao alocacao = alocacaoService.retonaAlocacaoPorOrder(order);
            return ReservaExibirDTO.builder().dataPrevistaEntrada(order.getDataPrevistaEntrada())
                    .dataPrevistaSaida(order.getDataPrevistaSaida())
                    .id(order.getId())
                    .veiculoSelecionado(CarroDTO.builder().id(alocacao.getCarro().getId())
                            .modelo(alocacao.getCarro().getModelo())
                            .placa(alocacao.getCarro().getPlaca()).build())
                    .userExiste(true)
                    .build();
        }
        return ReservaExibirDTO.builder().dataPrevistaEntrada(order.getDataPrevistaEntrada())
                .dataPrevistaSaida(order.getDataPrevistaSaida())
                .veiculos(usuario.getCarro().stream().filter(Carro::isAtivo).map(v->CarroDTO.builder()
                        .id(v.getId())
                        .modelo(v.getModelo())
                        .placa(v.getPlaca()).build()).collect(Collectors.toList()))
                .id(order.getId())
                .userExiste(false)
                .build();

    }

    @PostMapping("${autoparking.url.admin}/estacionamento/reservar")
    public String reservarVaga(@RequestParam("email") String email,
                                  @RequestParam("veiculo") Carro veiculo,
                                  @RequestParam("estacionamento") Estacionamento estacionamento,
                                  @RequestParam(defaultValue = "0") Order order,
                                  @RequestParam("dataPrevistaSaida") String dataPrevistaSaida,
                                  RedirectAttributes redirectAttribute,HttpSession session){

        if(Objects.isNull(order)){
            Usuario usuario = usuarioService.encontrarUsuarioPorUserName(email);
            alocacaoService.alocarVagaReservaAdmin(estacionamento,usuario,redirectAttribute,dataPrevistaSaida,veiculo);
        }else{
            orderService.mudarStatusOrdemComDataEntrada(order);
        }
        return "redirect:/admin/estacionamentos";
    }

    @GetMapping("${autoparking.url.admin}/orders")
    public String visualizarOrders(Model model,RedirectAttributes redirectAttribute,HttpSession session, Authentication authentication){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        Optional<Estacionamento> estacionamento = usuario.getEstacionamentos().stream().findFirst();
        model.addAttribute("orders",estacionamento.get().getOrder());
        model.addAttribute("servicos",estacionamento.get().getServicos().stream().filter(v->v.getTipoServico().equals(TipoServico.OUTRO)).collect(Collectors.toList()));
        return "/admin/orders";
    }

    @PostMapping("${autoparking.url.admin}/fatura/gerar")
    public String gerarFatura(@RequestParam("order") Order order,
                              @RequestParam(value="servicos", required = false)  Servico[] servicos,
                              Authentication authentication){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        faturaService.gerarFaturaPadrao(order,servicos);
        return "";
    }


}
