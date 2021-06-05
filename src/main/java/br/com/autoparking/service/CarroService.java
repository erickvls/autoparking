package br.com.autoparking.service;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;

import java.util.List;

public interface CarroService {
    List<Carro> listaCarrosPorUsuario(Usuario usuario);
    void salvarCarro(Carro carro);
    Carro encontrarVeiculoPeloUsuarioEIdVeiculo(Usuario usuario, Long id);
    void deletarCarroLogicamente(Carro carro,Usuario usuario);
}
