package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemVenda> itens;    

    @Column(name = "valor_total", nullable = false)
    private Double valor_total;

    @Column(name = "desconto", nullable = true)
    private Double desconto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "id_forma_pagamento", nullable = false)
    private FormaPagamento forma_pagamento;
}