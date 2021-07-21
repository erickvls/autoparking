package br.com.autoparking.service.impl;

import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.FaturaServicos;
import br.com.autoparking.model.Servico;
import br.com.autoparking.model.dto.ServicoFormDTO;
import br.com.autoparking.model.enums.TipoServico;
import br.com.autoparking.repository.ServicoRepository;
import br.com.autoparking.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public String salvar(ServicoFormDTO servicoDto, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(verificaExisteServico(servicoDto))) {
            Servico servico = Servico.builder()
                .tipoServico(servicoDto.getTipoServico())
                .descricao(servicoDto.getDescricao())
                .valor(new BigDecimal(servicoDto.getValor()))
                .estacionamento(servicoDto.getEstacionamento())
                .build();
            servicoRepository.save(servico);
            redirectAttributes.addFlashAttribute("mensagemSucesso","Serviço adicionado com sucesso!");
            return "redirect:/admin/estacionamentos";
        }
        redirectAttributes.addFlashAttribute("mensagemError","Já existe um serviço cadastrado nessa categoria.");
        return "redirect:/admin/estacionamentos";
    }

    @Override
    public Servico verificaExisteServico(ServicoFormDTO servicoFormDTO){
        if(servicoFormDTO.getTipoServico().equals(TipoServico.OUTRO)){
            return null;
        }
        return servicoRepository.findByEstacionamentoAndTipoServico(servicoFormDTO.getEstacionamento(), servicoFormDTO.getTipoServico());
    }

    @Override
    public void excluirServico(Servico servico) {
        servico.setExcluido(true);
        servicoRepository.save(servico);
    }
}
