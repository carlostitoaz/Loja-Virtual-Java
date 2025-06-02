package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final PessoaService pessoaService;
    private final FormaPagamentoService formaPagamentoService;

    public VendaService(VendaRepository vendaRepository, ProdutoService produtoService, ClienteService clienteService, PessoaService pessoaService, FormaPagamentoService formaPagamentoService) {
        this.vendaRepository = vendaRepository;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
        this.pessoaService = pessoaService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @Transactional
    public Venda create(Venda venda) {
        Double totalVenda = 0D;
        Double totalDesconto = 0D;

        venda.setCliente(clienteService.readById(venda.getCliente().getId_cliente()));
        venda.setPessoa(pessoaService.readById(venda.getPessoa().getId_pessoa()).get());
        venda.setForma_pagamento(formaPagamentoService.readById(venda.getForma_pagamento().getId_forma_pagamento()).get());

        if (venda.getItens() != null) {
            for (ItemVenda item : venda.getItens()) {
                Produto produto = produtoService.readById(item.getProduto().getId_produto());

                item.setProduto(produto);

                item.setVenda(venda);
                if(item.getDesconto() <= produto.getMax_desconto()) {
                    totalVenda += (item.getQuantidade() * produto.getPreco_venda()) - item.getDesconto();
                    item.setValor_total(totalVenda);
                    totalDesconto += item.getDesconto();
                }else{
                    throw new RuntimeException("Valor de desconto acima do esperado. Desconto aplicado: "+item.getDesconto()+". Máximo esperado: "+item.getProduto().getMax_desconto());
                }
            }
        }
        venda.setValor_total(totalVenda);
        venda.setDesconto(totalDesconto);
        return vendaRepository.save(venda);
    }

    public List<Venda> readAll() {
        return vendaRepository.findAll();
    }

    public Venda readById(Long id) {
        return vendaRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Venda não encontrada com ID "+id));
    }

    @Transactional
    public Venda update(Long id, Venda venda) {
        Venda vendaExistente = vendaRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Venda não encontrada com ID "+id));

        Double totalVenda = 0D;
        Double totalDesconto = 0D;

        vendaExistente.setCliente(clienteService.readById(venda.getCliente().getId_cliente()));
        vendaExistente.setPessoa(pessoaService.readById(venda.getPessoa().getId_pessoa()).get());
        vendaExistente.setForma_pagamento(formaPagamentoService.readById(venda.getForma_pagamento().getId_forma_pagamento()).get());
        
        vendaExistente.getItens().clear();

        if(venda.getItens() != null) {
            for(ItemVenda item : venda.getItens()) {
                Produto produto = produtoService.readById(item.getProduto().getId_produto());

                item.setProduto(produto);
                item.setVenda(vendaExistente);

                if(item.getDesconto() <= produto.getMax_desconto()) {
                    Double totalItem = (item.getQuantidade() * produto.getPreco_venda()) - item.getDesconto();
                    item.setValor_total(totalItem);
                    totalVenda += totalItem;
                    totalDesconto += item.getDesconto();
                }else{
                    throw new RuntimeException("Valor de desconto acima do esperado. Desconto aplicado: "+item.getDesconto()+". Máximo esperado: "+item.getProduto().getMax_desconto());
                }

                vendaExistente.getItens().add(item);
            }
        }

        vendaExistente.setValor_total(totalVenda);
        vendaExistente.setDesconto(totalDesconto);

        return vendaRepository.save(vendaExistente);
    }

    @Transactional
    public void delete(Long id) {
        if(!vendaRepository.existsById(id)) {
            throw new IdNaoEncontrado("Venda não contrada com ID "+id);
        }
        vendaRepository.deleteById(id);
    }
}
