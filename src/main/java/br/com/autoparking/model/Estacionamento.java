package br.com.autoparking.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "estacionamento")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String eixoX;
    @NotEmpty
    private String eixoY;
    private String telefone;
    @NotEmpty
    private String horarioAbre;
    @NotEmpty
    private String horarioFecha;
    @Min(1)
    private int quantidadeVagas;
    private BigDecimal precoHora;
    private BigDecimal precoFixo;

    @OneToMany(mappedBy="estacionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vaga> vaga;

    @JsonIgnore
    @OneToMany(mappedBy="estacionamento",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Servico> servicos;

    @OneToMany(mappedBy="estacionamento",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco")
    @NotNull
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name="usuario")
    private Usuario usuario;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEixoX() {
        return eixoX;
    }

    public void setEixoX(String eixoX) {
        this.eixoX = eixoX;
    }

    public String getEixoY() {
        return eixoY;
    }

    public void setEixoY(String eixoY) {
        this.eixoY = eixoY;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorarioAbre() {
        return horarioAbre;
    }

    public void setHorarioAbre(String horarioAbre) {
        this.horarioAbre = horarioAbre;
    }

    public String getHorarioFecha() {
        return horarioFecha;
    }

    public void setHorarioFecha(String horarioFecha) {
        this.horarioFecha = horarioFecha;
    }

    public int getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(int quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
    }

    public BigDecimal getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(BigDecimal precoHora) {
        this.precoHora = precoHora;
    }

    public BigDecimal getPrecoFixo() {
        return precoFixo;
    }

    public void setPrecoFixo(BigDecimal precoFixo) {
        this.precoFixo = precoFixo;
    }

    public Set<Vaga> getVaga() {
        return vaga;
    }

    public void setVaga(Set<Vaga> vaga) {
        this.vaga = vaga;
    }

    public Set<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Set<Servico> servicos) {
        this.servicos = servicos;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estacionamento that = (Estacionamento) o;
        return id == that.id &&
                quantidadeVagas == that.quantidadeVagas &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(eixoX, that.eixoX) &&
                Objects.equals(eixoY, that.eixoY) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(horarioAbre, that.horarioAbre) &&
                Objects.equals(horarioFecha, that.horarioFecha) &&
                Objects.equals(precoHora, that.precoHora) &&
                Objects.equals(precoFixo, that.precoFixo) &&
                Objects.equals(vaga, that.vaga) &&
                Objects.equals(servicos, that.servicos) &&
                Objects.equals(order, that.order) &&
                Objects.equals(endereco, that.endereco) &&
                Objects.equals(usuario, that.usuario);
    }

}
