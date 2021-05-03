package br.com.autoparking.validations;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class UsuarioExisteValidator implements ConstraintValidator<UsuarioExisteValid,String> {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void initialize(UsuarioExisteValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return usuariosIsAvailable(value);
    }

    public boolean usuariosIsAvailable(String username) {
        Optional<Usuario> usuario = usuarioRepository.findByUserName(username);
        return usuario.map(Usuario::getId).isEmpty();
    }
}
