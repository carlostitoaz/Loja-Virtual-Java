package br.edu.iff.ccc.bsi.foreverfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByUsuario(String usuario);
}
