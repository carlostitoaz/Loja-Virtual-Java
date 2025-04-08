package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import br.edu.iff.ccc.bsi.foreverfashion.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @Transactional
    public Venda create(Venda venda) {
        return vendaRepository.save(venda);
    }

    public List<Venda> readAll() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> readById(Long id) {
        return vendaRepository.findById(id);
    }

    @Transactional
    public Venda update(Long id, Venda venda) {
        if (!vendaRepository.existsById(id)) {
            throw new RuntimeException("Venda não encontrada.");
        }
        venda.setId_venda(id);
        return vendaRepository.save(venda);
    }

    @Transactional
    public boolean delete(Long id) {
        if (vendaRepository.existsById(id)) {
            vendaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
