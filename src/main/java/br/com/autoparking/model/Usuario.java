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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario",uniqueConstraints={@UniqueConstraint(columnNames={"cpf","userName"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @UsuarioExisteValid(message="Já existe um usuário cadastrado com esse email.")
    @Column(unique = true)
    private String userName;
    @NotEmpty(message = "O campo senha não pode está vazio")
    @Size(min = 6,max = 12, message = "A senha deve conter entre 6 a 12 caracteres.")
    private String password;
    private boolean ativo;
    @NotEmpty(message = "O campo nome não pode estar em branco.")
    private String nome;

    @CPF(message = "CPF insediro não é válido")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    private Date dataCriacao;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();


}
