package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.repository.ItemVendaRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;

    public ItemVendaService(ItemVendaRepository itemVendaRepository) {
        this.itemVendaRepository = itemVendaRepository;
    }

    @Transactional
    public ItemVenda create(ItemVenda itemVenda) {
        return itemVendaRepository.save(itemVenda);
    }

    public List<ItemVenda> readAll() {
        return itemVendaRepository.findAll();
    }

    public Optional<ItemVenda> readById(Long id) {
        return itemVendaRepository.findById(id);
    }

    @Transactional
    public ItemVenda update(Long id, ItemVenda itemVenda) {
        if (!itemVendaRepository.existsById(id)) {
            throw new RuntimeException("ItemVenda não encontrado.");
        }
        itemVenda.setId_item_venda(id);
        return itemVendaRepository.save(itemVenda);
    }

    @Transactional
    public boolean delete(Long id) {
        if (itemVendaRepository.existsById(id)) {
            itemVendaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}