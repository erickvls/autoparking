package br.com.autoparking.controller.admin;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.Cor;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.service.EstadoService;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class GestorController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("${autoparking.url.admin}/gestor")
    public String cadastrarGestor(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("estado", estadoService.listarTodosEstados());
        model.addAttribute("genero", Genero.values());
        return "admin/gestores/novo";
    }

    @PostMapping("${autoparking.url.admin}/gestor")
    public String salvarGestor(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            model.addAttribute("estado", estadoService.listarTodosEstados());
            model.addAttribute("genero", Genero.values());
            return "admin/gestores/novo";
        }

        Usuario criador = (Usuario) session.getAttribute("user");
        usuarioService.criarNovoGestor(criador,usuario,"ROLE_GESTOR");
        redirectAttributes.addFlashAttribute("mensagemSucesso","Gestor cadastrado com sucesso!");
        return "redirect:/admin/";
    }

    @GetMapping("${autoparking.url.admin}/gestores")
    public String listarGestores(Model model,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Usuario> listaGestores = usuarioService.listarGestores(usuario);
        model.addAttribute("listaGestores", listaGestores);
        return "admin/gestores/gestores";
    }

    @GetMapping("${autoparking.url.admin}/gestores/{id}")
    public String paginaEditarGestor(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        Usuario gestor = usuarioService.encontrarUsuarioPorId(id);
        if(Objects.isNull(gestor)){
            redirectAttributes.addFlashAttribute("mensagemError", "Usuário não encontrado");
            return "redirect:/admin";
        }
        model.addAttribute("gestor", gestor);
        return "admin/gestores/editar-gestor";
    }

    @PostMapping("${autoparking.url.admin}/gestores")
    public String salvarGestor(Usuario usuario,Model model,HttpSession httpSession){
        usuarioService.salvarUsuario(usuario);
        model.addAttribute("mensagemSucesso", "Gestor editado com sucesso.");
        return listarGestores(model,httpSession);
    }
}
