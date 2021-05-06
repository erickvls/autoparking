package br.com.autoparking.validations;

import br.com.autoparking.model.Usuario;
import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class UsuarioExisteValidator implements ConstraintValidator<UsuarioExisteValid,String> {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public void initialize(UsuarioExisteValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Usuario usuario = usuarioService.encontrarUsuarioPorUserName(value);
        return Objects.isNull(usuario.getUserName());
    }
}
