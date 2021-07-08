package br.com.autoparking.service;

import br.com.autoparking.model.Servico;
import br.com.autoparking.model.dto.ServicoFormDTO;
import br.com.autoparking.model.enums.TipoServico;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ServicoService {
    String salvar(ServicoFormDTO servico, RedirectAttributes redirectAttributes);
    Servico verificaExisteServico(ServicoFormDTO servicoFormDTO);
    void excluirServico(Servico servico);
}
