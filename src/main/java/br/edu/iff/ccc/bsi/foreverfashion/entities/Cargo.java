package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cargo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cargo implements Serializable{
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cargo;

    @Column(nullable = false, length = 100, unique = true)
    private String descricao;
}
