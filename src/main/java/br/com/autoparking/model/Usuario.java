package br.com.autoparking.model;

import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.validations.UsuarioExisteValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "O campo email não pode estar em branco.")
    @Email(message = "Preencha um email válido")
    @UsuarioExisteValid(message="Já existe um usuário cadastrado com esse email.")
    private String userName;
    @NotEmpty(message = "O campo senha não pode está vazio")
    @Size(min = 6,max = 12, message = "A senha deve conter entre 6 a 12 caracteres.")
    private String password;
    private boolean ativo;
    @NotEmpty(message = "O campo nome não pode estar em branco.")
    private String nome;
    @NotEmpty(message = "O campo CPF não pode estar em branco.")
    @CPF(message = "O CPF informado não é valido")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
