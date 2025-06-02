package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public Produto create(Produto produto) {
        Optional<Produto> produtoExistente = produtoRepository.findByDescricao(produto.getDescricao());
        if(produtoExistente.isPresent()) {
            throw new RuntimeException("Produto já cadastrado com nome fornecido.");
        }

        produto.setCategoria(categoriaService.readById(produto.getCategoria().getId_categoria()));

        return produtoRepository.save(produto);
    }

    public List<Produto> readAll() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto update(Long id, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(produto.getId_produto()).orElseThrow(() -> new IdNaoEncontrado("Produto não encontrado com ID: "+id));

        Optional<Produto> produtoDescricaoBuscada = produtoRepository.findByDescricao(produto.getDescricao());
        if(produtoDescricaoBuscada.isPresent() && !produtoDescricaoBuscada.get().getId_produto().equals(id) ) {
            throw new JaCadastrado("Produto já cadastrado com nome fornecido.");
        }

        produtoExistente.setCategoria(categoriaService.readById(produto.getCategoria().getId_categoria()));

        produtoExistente.setMarca(produto.getMarca());
        produtoExistente.setTamanho(produto.getTamanho());
        produtoExistente.setCor(produto.getCor());
        produtoExistente.setPreco_custo(produto.getPreco_custo());
        produtoExistente.setPreco_venda(produto.getPreco_venda());
        produtoExistente.setMax_desconto(produto.getMax_desconto());
        produtoExistente.setQuantidade(produto.getQuantidade());
        produtoExistente.setMaterial(produto.getMaterial());
        produtoExistente.setData_entrada(produto.getData_entrada());

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

    public Produto readById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Produto não encontrado com ID "+id));
    }
}