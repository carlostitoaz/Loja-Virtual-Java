package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Endereco;
import br.edu.iff.ccc.bsi.foreverfashion.repository.EnderecoRepository;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Endereco create(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> readAll() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> readById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Transactional
    public Endereco update(Long id, Endereco endereco) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado.");
        }
        endereco.setId_endereco(id);
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public boolean delete(Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
