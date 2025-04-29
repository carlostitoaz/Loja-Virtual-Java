package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
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
            throw new JaCadastrado("Cliente já cadastrado com CPF fornecido.");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> readAll() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente update(Long id, Cliente cliente) {
        if(!clienteRepository.existsById(id)) {
            throw new IdNaoEncontrado("Cliente não encontrado com ID: "+id);
        }
        cliente.setId_cliente(id);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Long id){
        if(!clienteRepository.existsById(id)){
            throw new IdNaoEncontrado("Cliente não encontrado com ID: "+id);
        }
        clienteRepository.deleteById(id);
    }

    public Cliente readById(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Cliente não encontrado com ID: "+id));
    }   
}
