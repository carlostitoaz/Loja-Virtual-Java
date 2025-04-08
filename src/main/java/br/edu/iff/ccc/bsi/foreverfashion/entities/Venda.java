package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venda implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venda;
 
    private LocalDateTime data;

    @PrePersist
    public void PrePersist(){
        this.data = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;    

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa Pessoa;

    @OneToOne
    @JoinColumn(name = "id_forma_pagamento", nullable = false)
    private FormaPagamento forma_pagamento;
}
