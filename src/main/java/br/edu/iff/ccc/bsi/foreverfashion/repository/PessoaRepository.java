package br.edu.iff.ccc.bsi.foreverfashion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    boolean existsByEmail(String email);
    Optional<Pessoa> findByEmail(String email);
    Optional<Pessoa> findByTelefone(String telefone);
}
