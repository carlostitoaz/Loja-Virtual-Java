package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "forma_pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FormaPagamento implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_forma_pagamento;

    @Column(nullable = false, length = 100)
    private String descricao;
}
