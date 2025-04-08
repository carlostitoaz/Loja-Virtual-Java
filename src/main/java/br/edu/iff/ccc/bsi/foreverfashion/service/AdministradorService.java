package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
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
            throw new RuntimeException("Administrador já cadastrado com descrição fornecida.");
        }   
        return administradorRepository.save(administrador);
    }

    public List<Administrador> readAll() {
        return administradorRepository.findAll();
    }   

    @Transactional
    public Administrador update(Long id, Administrador administrador) {
        if (!administradorRepository.existsById(id)) {
            throw new RuntimeException("Administrador não encontrado");
        }
        administrador.setId_pessoa(id); 
        return administradorRepository.save(administrador);
    }

    @Transactional
    public boolean delete(Long id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Administrador> readById(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new RuntimeException("Administrador não encontrado");
        }
        return administradorRepository.findById(id);
    }
}
