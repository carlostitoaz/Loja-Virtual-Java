package br.edu.iff.ccc.bsi.foreverfashion.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pessoa implements Serializable{
    public static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 15)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo id_cargo;

    @OneToMany(mappedBy = "endereco")
    private Endereco id_endereco;
}
