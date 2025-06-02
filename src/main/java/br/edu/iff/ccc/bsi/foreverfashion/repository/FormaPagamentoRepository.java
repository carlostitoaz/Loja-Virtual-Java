package br.edu.iff.ccc.bsi.foreverfashion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    Boolean existsByDescricao(String descricao);
    Optional<FormaPagamento> findByDescricao(String descricao);
}
