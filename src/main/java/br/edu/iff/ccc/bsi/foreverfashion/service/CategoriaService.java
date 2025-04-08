package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;
import br.edu.iff.ccc.bsi.foreverfashion.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Categoria create(Categoria categoria) {
        if (categoriaRepository.existsByDescricao(categoria.getDescricao())) {
            throw new RuntimeException("Categoria já cadastrada com nome fornecido.");
        }   
        return categoriaRepository.save(categoria);
    } 

    public List<Categoria> readAll() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria update(Long id, Categoria categoria){
        if(!categoriaRepository.existsById(id)){
            throw new RuntimeException("Categoria não encontrada");
        }
        categoria.setId_categoria(id);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public boolean delete(Long id){
        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Categoria> readById(Long id){
        if(!categoriaRepository.existsById(id)){
           throw new RuntimeException("Categoria não encontrada");
        }
        return categoriaRepository.findById(id);
    }
}
