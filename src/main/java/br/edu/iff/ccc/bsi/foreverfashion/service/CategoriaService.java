package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
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
            throw new JaCadastrado("Categoria já cadastrada com nome fornecido.");
        }   
        return categoriaRepository.save(categoria);
    } 

    public List<Categoria> readAll() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria update(Long id, Categoria categoria){
        if(!categoriaRepository.existsById(id)){
            throw new IdNaoEncontrado("Categoria não encontrada com ID "+id);
        }

        Optional<Categoria> categoriaBuscada = findByDescricao(categoria.getDescricao());
        if(categoriaBuscada.isPresent() && !categoriaBuscada.get().getId_categoria().equals(id)){
            throw new JaCadastrado("Categoria já cadastrada com nome fornecido.");
        }

        categoria.setId_categoria(id);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new IdNaoEncontrado("Categoria não encontrada com ID "+id);
        }
        categoriaRepository.deleteById(id);
    }

    public Categoria readById(Long id){
       return categoriaRepository.findById(id).orElseThrow(()->  new IdNaoEncontrado("Categoria não encontrada com ID "+id));
    }

    public Optional<Categoria> findByDescricao(String descricao) {
        return categoriaRepository.findByDescricao(descricao);
    }
}
