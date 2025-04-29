package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.AdministradorRepository;
import jakarta.transaction.Transactional;

@Service
public class AdministradorService {
    private final AdministradorRepository administradorRepository;  

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Transactional
    public Administrador create(Administrador administrador) {
        if (administradorRepository.existsByDescricao(administrador.getDescricao())) {
            throw new JaCadastrado("Administrador já cadastrado com descrição fornecida.");
        }
        return administradorRepository.save(administrador);
    }

    public List<Administrador> readAll() {
        return administradorRepository.findAll();
    }   

    @Transactional
    public Administrador update(Long id, Administrador administrador) {
        if (!administradorRepository.existsById(id)) {
            throw new IdNaoEncontrado("Administrador não encontrado com ID "+id);
        }
        administrador.setId_pessoa(id); 
        return administradorRepository.save(administrador);
    }

    @Transactional
    public void delete(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new IdNaoEncontrado("Administrador não encontrado com ID "+id);
        }
        administradorRepository.deleteById(id); 
    }

    public Administrador readById(Long id) {
        return administradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Administrador não encontrado com ID "+id));
    }
    
}
