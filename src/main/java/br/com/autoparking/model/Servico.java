package br.com.autoparking.model;

import br.com.autoparking.model.enums.TipoServico;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "servico")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;

    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    @OneToMany(mappedBy="servico",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FaturaServicos> faturaServicos;

    @ManyToOne
    @JoinColumn(name="estacionamento_id", nullable=false)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JsonIgnore
    private Estacionamento estacionamento;

    private boolean excluido = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<FaturaServicos> getFaturaServicos() {
        return faturaServicos;
    }

    public void setFaturaServicos(Set<FaturaServicos> faturaServicos) {
        this.faturaServicos = faturaServicos;
    }

    public Estacionamento getEstacionamento() {
        return estacionamento;
    }

    public void setEstacionamento(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return id == servico.id &&
                Objects.equals(descricao, servico.descricao) &&
                Objects.equals(valor, servico.valor) &&
                Objects.equals(faturaServicos, servico.faturaServicos) &&
                Objects.equals(estacionamento, servico.estacionamento);
    }


}
