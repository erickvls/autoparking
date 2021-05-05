package br.com.autoparking.validations;

import br.com.autoparking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsuarioExisteValidator implements ConstraintValidator<UsuarioExisteValid,String> {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public void initialize(UsuarioExisteValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return usuarioService.usuariosIsAvailable(value);
    }
}
