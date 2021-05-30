package br.com.autoparking.service.impl;

import br.com.autoparking.model.Carro;
import br.com.autoparking.model.Usuario;
import br.com.autoparking.repository.CarroRepository;
import br.com.autoparking.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public List<Carro> listaCarrosPorUsuario(Usuario usuario) {
        return carroRepository.findCarroByUsuario(usuario);
    }

    @Override
    public void salvarCarro(Carro carro) {
        carroRepository.save(carro);
    }
}
