package br.com.autoparking.controller;

import br.com.autoparking.model.Fatura;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.dto.EstacionamentoForm;
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
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class EstatisticaController {

    @Autowired
    private EstatisticaServiceImpl estatisticaService;

    @GetMapping("${autoparking.url.admin}/estatisticas")
    public String estatistica(Model model, Authentication authentication, RedirectAttributes redirectAttributes, HttpSession session){
        return "admin/estatistica";
    }

    @PostMapping("${autoparking.url.admin}/estatisticas/saldo")
    public @ResponseBody String mostrarSaldo(@RequestParam("data") String data, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        return estatisticaService.mostrarSaldo(data,usuario).toString();
    }

    @PostMapping("${autoparking.url.admin}/estatisticas/semanal")
    public @ResponseBody List<Fatura> mostrarSaldo(
            @RequestParam("dataFrom") String dataFrom,
            @RequestParam("dataTo") String dataTo,
            HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Fatura> faturaList = estatisticaService.estatisticaSemanal(dataFrom,dataTo,usuario);
        return faturaList;
    }



}
