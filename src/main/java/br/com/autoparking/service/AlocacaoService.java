package br.com.autoparking.service;

import br.com.autoparking.model.Alocacao;
import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Estacionamento;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.model.enums.StatusVaga;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;

public interface AlocacaoService {
    Alocacao alocarVagaPorEstacionamento(StatusVaga statusVaga, Estacionamento estacionamento, Usuario usuario, RedirectAttributes redirectAttributes,
                                         LocalDate dataPrevista, Carro carro);
}
