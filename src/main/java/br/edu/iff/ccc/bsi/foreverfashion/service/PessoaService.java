package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.repository.PessoaRepository;
import jakarta.transaction.Transactional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa create(Pessoa pessoa) {
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new RuntimeException("Pessoa já cadastrada com o email fornecido.");
        }
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> readAll() {
        return pessoaRepository.findAll();
    }

    @Transactional
    public Pessoa update(Long id, Pessoa pessoa) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        pessoa.setId_pessoa(id);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public boolean delete(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Pessoa> readById(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        return pessoaRepository.findById(id);
    }
}
