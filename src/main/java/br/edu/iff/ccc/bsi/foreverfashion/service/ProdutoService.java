package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import br.edu.iff.ccc.bsi.foreverfashion.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto create(Produto produto) {
        Optional<Produto> produtoExistente = produtoRepository.findByDescricao(produto.getDescricao());
        if(produtoExistente.isPresent()) {
            throw new RuntimeException("Produto já cadastrado com nome fornecido.");
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> readAll() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto update(Long id, Produto produto) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produto.setId_produto(id);
        return produtoRepository.save(produto);
    }

    @Transactional
    public boolean delete(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Produto> readById(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        return produtoRepository.findById(id);
    }
}