package br.com.autoparking.service.impl;

import br.com.autoparking.service.CryptService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CryptServiceImpl implements CryptService {

    @Override
    public String gerar() {
        UUID uuid = UUID.randomUUID();
        String senhaBase = uuid.toString();
        return senhaBase.substring(0, 8);
    }
}
