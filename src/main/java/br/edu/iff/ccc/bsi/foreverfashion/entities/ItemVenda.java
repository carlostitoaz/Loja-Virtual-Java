package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemVenda implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_item_venda;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private Double valor_unitario;

    @Column(nullable = false)
    private Double valor_total;

    @ManyToOne
    @JoinColumn(name = "id_venda", nullable = false)
    @JsonBackReference
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;
}
