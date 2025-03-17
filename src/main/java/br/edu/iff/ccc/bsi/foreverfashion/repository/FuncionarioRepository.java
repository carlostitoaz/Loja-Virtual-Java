package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
} 