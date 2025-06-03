package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder codificador;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder codificador) {
        this.usuarioRepository = usuarioRepository;
        this.codificador = codificador;
    }

    @Transactional
    public Usuario create(Usuario usuario) {
        if (existsByUsuario(usuario.getUsuario())) {
            throw new JaCadastrado("Usuário já cadastrado com nome fornecido.");
        }   

        if(usuario.getUsuario() == null || usuario.getSenha() == null) {
            throw new RuntimeException("Usuário ou senha não podem ser em branco.");
        }

        String senhaCodificada = codificador.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);

        return usuarioRepository.save(usuario);
    } 

    public List<Usuario> readAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario update(Long id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Usuário não encontrado."));

        Optional<Usuario> usuarioBuscado = usuarioRepository.findByUsuario(usuario.getUsuario());
        if(usuarioBuscado.isPresent() && !usuarioBuscado.get().getId_usuario().equals(id)) {
            throw new JaCadastrado("Usuário já cadastrado com nome fornecido.");
        }

        if(usuario.getUsuario() == null || usuario.getSenha() == null) {
            throw new RuntimeException("Usuário ou senha não podem ser em branco.");
        }

        String senhaCodificada = codificador.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);

        usuarioExistente.setUsuario(usuario.getUsuario());
        usuarioExistente.setSenha(usuario.getSenha());

        usuario.setId_usuario(id);
        return usuarioRepository.save(usuarioExistente);
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
           throw new IdNaoEncontrado("Usuário não encontrado");
        }
        return usuarioRepository.findById(id);
    }

    public boolean existsByUsuario(String usuario) {
        return usuarioRepository.existsByUsuario(usuario);
    }

    public Optional<Usuario> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Optional<Usuario> findByUsuarioAndSenha(String usuario, String senha) {
        return usuarioRepository.findByUsuarioAndSenha(usuario, senha);
    }
}
