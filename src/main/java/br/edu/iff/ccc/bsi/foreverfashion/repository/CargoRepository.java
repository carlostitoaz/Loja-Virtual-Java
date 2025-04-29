package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Boolean existsByDescricao(String descricao);
}
