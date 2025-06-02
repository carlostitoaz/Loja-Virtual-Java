package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private Double preco_custo;

    @Column(nullable = false)
    private Double preco_venda;

    @Column(nullable = false)
    private Double max_desconto;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(nullable = false, length = 100)
    private String material;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_entrada;
}
