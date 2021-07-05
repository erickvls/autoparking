package br.com.autoparking.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    private LocalDateTime data;
    private BigDecimal total;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy="fatura",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FaturaServicos> faturaServicos;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToOne(mappedBy = "fatura")
    private Order order;

}
