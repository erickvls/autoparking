package br.com.autoparking.service.impl;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.repository.CarroRepository;
import br.com.autoparking.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public List<Carro> listaCarrosPorUsuario(Usuario usuario) {
        return carroRepository.findCarroByUsuarioAndExcluidoIsFalse(usuario);
    }

    @Override
    public void salvarCarro(Carro carro) {
        carro.setModelo(carro.getModelo().toUpperCase());
        carro.setPlaca(carro.getPlaca().toUpperCase());
        carroRepository.save(carro);
    }

    @Override
    public Carro encontrarVeiculoPeloUsuarioEIdVeiculo(Usuario usuario, Long id) {
        return carroRepository.findCarroByUsuarioAndId(usuario, id);
    }

    @Override
    public void deletarCarroLogicamente(Carro carro, Usuario usuario) {
        Carro car = encontrarVeiculoPeloUsuarioEIdVeiculo(usuario,carro.getId());
        car.setExcluido(true);
        carroRepository.save(car);
    }

}
