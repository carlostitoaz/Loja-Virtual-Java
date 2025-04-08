package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.repository.*;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("Cliente já cadastrado com CPF fornecido.");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> readAll() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente update(Long id, Cliente cliente) {
        if(!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        cliente.setId_cliente(id);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public boolean delete(Long id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Cliente> readById(Long id){
        if(!clienteRepository.existsById(id)){
            throw new RuntimeException("Cliente não encontrado");
        }
        return clienteRepository.findById(id);
    }   
}
