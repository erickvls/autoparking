package br.com.autoparking.model;

import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userName;
    @NotEmpty(message = "A senha deve conter entre 6 a 12 caracteres.")
    @Size(min = 6,max = 12, message = "")
    private String password;
    private boolean ativo;
    @NotEmpty(message = "O campo nome não pode estar em branco.")
    private String nome;
    @NotEmpty(message = "O campo telefone não pode estar em branco.")
    private String telefone;
    @NotEmpty(message = "O campo CPF não pode estar em branco.")
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
