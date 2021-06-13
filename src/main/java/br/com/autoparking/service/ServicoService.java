package br.com.autoparking.service;

import br.com.autoparking.model.Servico;
import br.com.autoparking.model.dto.ServicoFormDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ServicoService {
    String salvar(ServicoFormDTO servico, RedirectAttributes redirectAttributes);
}
