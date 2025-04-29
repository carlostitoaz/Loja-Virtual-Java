package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Boolean existsByDescricao(String descricao);
}