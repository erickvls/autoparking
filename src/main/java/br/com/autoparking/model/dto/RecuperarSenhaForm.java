package br.com.autoparking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaForm {
    @NotEmpty(message = "Preencha com seu email.")
    private String email;
}
