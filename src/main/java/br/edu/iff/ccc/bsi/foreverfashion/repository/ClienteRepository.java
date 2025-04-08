package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.*;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Boolean existsByCpf(String cpf);
}