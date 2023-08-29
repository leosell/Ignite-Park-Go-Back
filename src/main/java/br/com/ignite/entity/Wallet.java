package br.com.ignite.entity;

import java.math.BigDecimal;
import java.util.List;

import br.com.ignite.enums.TipoPagamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_wallet")
@AllArgsConstructor
public class Wallet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "TIPO_PAGAMENTO")
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<History> history;
    
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
