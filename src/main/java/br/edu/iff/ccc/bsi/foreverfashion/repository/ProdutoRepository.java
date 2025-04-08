package br.edu.iff.ccc.bsi.foreverfashion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    public Optional<Produto> findByDescricao(String descricao);
}