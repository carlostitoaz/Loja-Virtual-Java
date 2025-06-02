package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.FormaPagamento;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.FormaPagamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class FormaPagamentoService {
    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional
    public FormaPagamento create(FormaPagamento formaPagamento) {
        if (formaPagamentoRepository.existsByDescricao(formaPagamento.getDescricao())) {
            throw new JaCadastrado("Forma de pagamento já cadastrada com nome fornecido.");
        }   
        return formaPagamentoRepository.save(formaPagamento);
    }

    public List<FormaPagamento> readAll() {
        return formaPagamentoRepository.findAll();
    }
    
    @Transactional
    public FormaPagamento update(Long id, FormaPagamento formaPagamento){
        if(!formaPagamentoRepository.existsById(id)){
            throw new IdNaoEncontrado("Forma de pagamento não encontrada");
        }

        Optional<FormaPagamento> formaPagamentoBuscada = formaPagamentoRepository.findByDescricao(formaPagamento.getDescricao());
        if(formaPagamentoBuscada.isPresent() && !formaPagamentoBuscada.get().getId_forma_pagamento().equals(id)) {
            throw new JaCadastrado("Forma de pagamento já cadastrada com nome fornecido.");
        }
        formaPagamento.setId_forma_pagamento(id);
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void delete(Long id){
        if(formaPagamentoRepository.existsById(id)){
            throw new IdNaoEncontrado("Forma de pagamento não encontrada.");
        }
        formaPagamentoRepository.deleteById(id);
    }

    public Optional<FormaPagamento> readById(Long id){
        if(!formaPagamentoRepository.existsById(id)){
           throw new IdNaoEncontrado("Forma de pagamento não encontrada");
        }
        return formaPagamentoRepository.findById(id);      
    }

    public Optional<FormaPagamento> findByDescricao(String descricao) {
        return formaPagamentoRepository.findByDescricao(descricao);
    }
}
