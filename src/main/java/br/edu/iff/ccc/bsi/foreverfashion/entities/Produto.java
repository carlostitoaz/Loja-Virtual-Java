package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Produto implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 100)
    private String tamanho;

    @Column(nullable = false, length = 100)
    private String cor;

    @Column(nullable = false)
    private double preco_custo;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria id_categoria;

    @Column(nullable = false, length = 100)
    private String material;

    @Column(nullable = false)
    private Date data_entrada;
}
