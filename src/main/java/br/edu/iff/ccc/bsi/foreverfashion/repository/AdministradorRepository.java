package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    boolean existsByDescricao(String descricao);
}
