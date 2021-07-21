package br.com.autoparking.controller;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.FaturaDTO;
import br.com.autoparking.service.UsuarioService;
import br.com.autoparking.service.impl.EstatisticaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class EstatisticaController {

    @Autowired
    private EstatisticaServiceImpl estatisticaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("${autoparking.url.admin}/estatisticas")
    public String estatistica(Model model, Authentication authentication, RedirectAttributes redirectAttributes, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        long qtdGestores = usuarioService.quantidadeGestoresPorEstacionamento(usuario);
        model.addAttribute("qtdGestores",qtdGestores);
        return "admin/estatistica";
    }

    @PostMapping("${autoparking.url.admin}/estatisticas/saldo")
    public @ResponseBody String mostrarSaldo(@RequestParam("data") String data, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        return estatisticaService.mostrarSaldo(data,usuario).toString();
    }

    @PostMapping("${autoparking.url.admin}/estatisticas/semanal")
    public @ResponseBody List<FaturaDTO> mostrarSaldoSemanal(
            @RequestParam("dataFrom") String dataFrom,
            @RequestParam("dataTo") String dataTo,
            HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("user");
        return estatisticaService.estatisticaSemanal(dataFrom,dataTo,usuario);
    }

    @PostMapping("${autoparking.url.admin}/estatisticas/usuarios")
    public @ResponseBody Long mostrarUsuariosMensal(@RequestParam("mes") String mes,@RequestParam("ano") String ano, HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("user");
        return estatisticaService.estatisticaUsuarioPorMes(mes,ano,usuario);
    }


}
