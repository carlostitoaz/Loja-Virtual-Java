package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario create(Usuario usuario) {
        if (usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            throw new RuntimeException("Usuario já cadastrada com nome fornecido.");
        }   
        return usuarioRepository.save(usuario);
    } 

    public List<Usuario> readAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario update(Long id, Usuario usuario){
        if(!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuario não encontrada");
        }
        usuario.setId_usuario(id);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public boolean delete(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Usuario> readById(Long id){
        if(!usuarioRepository.existsById(id)){
           throw new RuntimeException("Usuario não encontrada");
        }
        return usuarioRepository.findById(id);
    }
}
