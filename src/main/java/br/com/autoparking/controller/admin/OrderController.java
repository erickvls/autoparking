package br.com.autoparking.controller.admin;

import br.com.autoparking.model.*;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class OrderController {

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


    @GetMapping("${autoparking.url.admin}/orders")
    public String visualizarOrders(Model model, RedirectAttributes redirectAttribute, HttpSession session, Authentication authentication){
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(authentication.getName());
        Optional<Estacionamento> estacionamento;
        if(Objects.isNull(usuario.getCriador())){
            estacionamento = usuario.getEstacionamentos().stream().findFirst();
        }else{
            estacionamento = usuario.getCriador().getEstacionamentos().stream().findFirst();
        }
        if(estacionamento.isEmpty()) {
            model.addAttribute("orders", Collections.EMPTY_LIST);
            model.addAttribute("servicos", Collections.EMPTY_LIST);
            return "admin/orders";
        }
        model.addAttribute("orders",estacionamento.get().getOrder());
        model.addAttribute("servicos",estacionamento.get().getServicos().stream().filter(v->v.getTipoServico().equals(TipoServico.OUTRO)).collect(Collectors.toList()));
        return "admin/orders";
    }

    @PostMapping("${autoparking.url.admin}/fatura/gerar")
    public String gerarFatura(@RequestParam("order") Order order,
                              @RequestParam(value="servicos", required = false)  Servico[] servicos,
                              Authentication authentication,
                              Model model){
        Fatura fatura = faturaService.gerarFaturaPadrao(order,servicos);
        model.addAttribute("order",fatura.getOrder());
        model.addAttribute("fatura",fatura);
        return "admin/fatura/index";
    }

    @GetMapping("/fatura/visualizar/{order}")
    public String visualizarFatura(@PathVariable(value="order",required = false) Order order, Model model){
        if(Objects.isNull(order) || Objects.isNull(order.getFatura())){
            return "error";
        }
        model.addAttribute("fatura",order.getFatura());
        return "admin/fatura/index";
    }

    @PostMapping("${autoparking.url.admin}/servicos/excluir/")
    public String excluirServico(@ModelAttribute("servico") Servico servico, HttpSession session, Model model){
        servicoService.excluirServico(servico);
        return "redirect:/admin/estacionamentos";
    }
}
