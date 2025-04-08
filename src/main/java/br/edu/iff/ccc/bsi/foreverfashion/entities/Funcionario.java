package br.edu.iff.ccc.bsi.foreverfashion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "funcionario")    
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Funcionario extends Pessoa{
    public static final long serialVersionUID = 1L;
/* 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_funcionario;
*/
    @Column(nullable = false)
    private Double salario;
}