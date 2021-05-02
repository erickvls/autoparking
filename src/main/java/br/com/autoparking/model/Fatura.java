package br.com.autoparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "fatura")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date data;
    private BigDecimal total;

    @OneToMany(mappedBy="fatura")
    private Set<FaturaServicos> faturaServicos;

    @OneToOne(mappedBy = "fatura")
    private Order order;


}
