package br.com.autoparking.model;

import br.com.autoparking.model.enums.AuthenticationProvider;
import br.com.autoparking.model.enums.Genero;
import br.com.autoparking.validations.UsuarioExisteValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuario",uniqueConstraints={@UniqueConstraint(columnNames={"cpf","userName"})})
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

    @CPF(message = "CPF inserido não é válido")
    private String cpf;

    private String telefone;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco")
    private Endereco endereco;

    @OneToMany(mappedBy="usuario", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Order> order;

    @OneToMany(mappedBy="usuario",  fetch = FetchType.EAGER)
    private Set<Carro> carro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "formaPagamento")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy="usuario", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Estacionamento> estacionamentos;

    private boolean senhaResetada;

    private boolean perfilAtualizado = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Set<Carro> getCarro() {
        return carro;
    }

    public void setCarro(Set<Carro> carro) {
        this.carro = carro;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Set<Estacionamento> getEstacionamentos() {
        return estacionamentos;
    }

    public void setEstacionamentos(Set<Estacionamento> estacionamentos) {
        this.estacionamentos = estacionamentos;
    }

    public boolean isSenhaResetada() {
        return senhaResetada;
    }

    public void setSenhaResetada(boolean senhaResetada) {
        this.senhaResetada = senhaResetada;
    }

    public boolean isPerfilAtualizado() {
        return perfilAtualizado;
    }

    public void setPerfilAtualizado(boolean perfilAtualizado) {
        this.perfilAtualizado = perfilAtualizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id &&
                ativo == usuario.ativo &&
                senhaResetada == usuario.senhaResetada &&
                perfilAtualizado == usuario.perfilAtualizado &&
                Objects.equals(userName, usuario.userName) &&
                Objects.equals(password, usuario.password) &&
                Objects.equals(nome, usuario.nome) &&
                Objects.equals(cpf, usuario.cpf) &&
                Objects.equals(telefone, usuario.telefone) &&
                genero == usuario.genero &&
                authProvider == usuario.authProvider &&
                Objects.equals(dataCriacao, usuario.dataCriacao) &&
                Objects.equals(roles, usuario.roles) &&
                Objects.equals(endereco, usuario.endereco) &&
                Objects.equals(order, usuario.order) &&
                Objects.equals(carro, usuario.carro) &&
                Objects.equals(formaPagamento, usuario.formaPagamento) &&
                Objects.equals(estacionamentos, usuario.estacionamentos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, ativo, nome, cpf, telefone, genero, authProvider, dataCriacao, roles, endereco, order, carro, formaPagamento, estacionamentos, senhaResetada, perfilAtualizado);
    }
}
