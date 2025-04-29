package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    boolean existsByEmail(String email);
}
