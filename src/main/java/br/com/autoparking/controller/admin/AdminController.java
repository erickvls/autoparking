package br.com.autoparking.controller.admin;

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
import java.util.Collections;
import java.util.HashSet;
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
    public String homeAdmin(Authentication authentication,Model model){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        if(Objects.isNull(usuario.getCriador())){
            Estacionamento est = usuario.getEstacionamentos().stream().findFirst().orElse(Estacionamento.builder().vaga(new HashSet<>()).build());
            model.addAttribute("vagasTotais",usuario.getEstacionamentos().stream().findFirst().orElse(est).getQuantidadeVagas());
            model.addAttribute("vagasOcupadas",usuario.getEstacionamentos().stream().findFirst().orElse(est).getVaga().stream().filter(v -> v.getStatus().equals(StatusVaga.OCUPADO)).count());
            model.addAttribute("vagasLivres",usuario.getEstacionamentos().stream().findFirst().orElse(est).getVaga().stream().filter(v -> v.getStatus().equals(StatusVaga.LIVRE) || v.getStatus().equals(StatusVaga.RESERVADO)).count());
        }else{
            model.addAttribute("vagasTotais",usuario.getCriador().getEstacionamentos().stream().findFirst().orElse(new Estacionamento()).getQuantidadeVagas());
            model.addAttribute("vagasOcupadas",usuario.getCriador().getEstacionamentos().stream().findFirst().orElse(new Estacionamento()).getVaga().stream().filter(v -> v.getStatus().equals(StatusVaga.OCUPADO)).count());
            model.addAttribute("vagasLivres",usuario.getCriador().getEstacionamentos().stream().findFirst().orElse(new Estacionamento()).getVaga().stream().filter(v -> v.getStatus().equals(StatusVaga.LIVRE) || v.getStatus().equals(StatusVaga.RESERVADO)).count());

        }
        return "admin/admin";
    }

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

}
