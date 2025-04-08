package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Funcionario;
import br.edu.iff.ccc.bsi.foreverfashion.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService{
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public Funcionario create(Funcionario funcionario) {
        if (funcionarioRepository.existsBySalario(funcionario.getSalario())) {
            throw new RuntimeException("Funcionário já cadastrado com salário fornecido.");
        }   
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> readAll() {
        return funcionarioRepository.findAll();
    }

    @Transactional
    public Funcionario update(Long id, Funcionario funcionario){
        if(!funcionarioRepository.existsById(id)){
            throw new RuntimeException("Funcionário não encontrado");
        }
        funcionario.setId_pessoa(id); 
        return funcionarioRepository.save(funcionario);
    }
    
    @Transactional
    public boolean delete(Long id){
        if(funcionarioRepository.existsById(id)){
            funcionarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Funcionario> readById(Long id){
        if(!funcionarioRepository.existsById(id)){
           throw new RuntimeException("Funcionário não encontrado");
        }
        return funcionarioRepository.findById(id);
    }
}
