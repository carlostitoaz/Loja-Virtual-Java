package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venda implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venda;

    @Column(nullable = false)
    private double valor_total;

    @Column(nullable = false)
    private int quantidadeItens;

    @Column(nullable = false)
    private String data_venda;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
