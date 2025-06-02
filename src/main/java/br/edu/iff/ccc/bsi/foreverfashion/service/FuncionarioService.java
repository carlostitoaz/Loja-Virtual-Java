package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Funcionario;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService{
    private final FuncionarioRepository funcionarioRepository;
    private final CargoService cargoService;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoService cargoService, PessoaService pessoaService, UsuarioService usuarioService){
        this.funcionarioRepository = funcionarioRepository;
        this.cargoService = cargoService;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Funcionario create(Funcionario funcionario) {
        if (funcionarioRepository.existsByEmail(funcionario.getEmail())) {
            throw new RuntimeException("Funcionário já cadastrado com email fornecido.");
        }   

        funcionario.setCargo(cargoService.readById(funcionario.getCargo().getId_cargo()));

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> readAll() {
        return funcionarioRepository.findAll();
    }

    @Transactional
    public Funcionario update(Long id, Funcionario funcionario){
        Funcionario funcionarioExistente = funcionarioRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Funcionário não encontrado com ID: "+id));
        
        funcionarioExistente.setNome(funcionario.getNome().toUpperCase().trim());
        funcionarioExistente.setCargo(cargoService.readById(funcionario.getCargo().getId_cargo()));
        funcionarioExistente.setSalario(funcionario.getSalario());

        Optional<Pessoa> funcionarioEmailBuscado = pessoaService.findByEmail(funcionario.getEmail());
        if(funcionarioEmailBuscado.isPresent() && !funcionarioEmailBuscado.get().getId_pessoa().equals(id)) {
            throw new JaCadastrado("Email já cadastrado.");
        }
        funcionarioExistente.setEmail(funcionario.getEmail());

        Optional<Pessoa> funcionarioTelBuscado = pessoaService.findByTelefone(funcionario.getTelefone());
        if(funcionarioTelBuscado.isPresent() && !funcionarioTelBuscado.get().getId_pessoa().equals(id)) {
            throw new JaCadastrado("Telefone já cadastrado.");
        }
        funcionarioExistente.setTelefone(funcionario.getTelefone());

        if(funcionario.getUsuario().getId_usuario() != null && funcionario.getUsuario().getSenha() != null) {
            Optional<Usuario> usuarioResultado = usuarioService.findByUsuario(funcionario.getUsuario().getUsuario());
            if(usuarioResultado.isPresent() && !usuarioResultado.get().getId_usuario().equals(id)) {
                throw new JaCadastrado("Usuário já cadastrado.");
            }
            funcionarioExistente.getUsuario().setUsuario(funcionario.getUsuario().getUsuario());
            funcionarioExistente.getUsuario().setSenha(funcionario.getUsuario().getSenha());
        }else{
            throw new RuntimeException("Usuário ou senha não podem ser em branco.");
        }

        if(funcionario.getEndereco() != null) {
            if(funcionarioExistente.getEndereco() == null) {
                funcionarioExistente.setEndereco(funcionario.getEndereco());
            } else {
                funcionarioExistente.getEndereco().setRua(funcionario.getEndereco().getRua());
                funcionarioExistente.getEndereco().setNumero(funcionario.getEndereco().getNumero());
                funcionarioExistente.getEndereco().setBairro(funcionario.getEndereco().getBairro());
                funcionarioExistente.getEndereco().setCidade(funcionario.getEndereco().getCidade());
                funcionarioExistente.getEndereco().setEstado(funcionario.getEndereco().getEstado());
                funcionarioExistente.getEndereco().setCep(funcionario.getEndereco().getCep());
            }
        }
        return funcionarioRepository.save(funcionarioExistente);
    }
    
    @Transactional
    public void delete(Long id){
        if(!funcionarioRepository.existsById(id)){
            throw new IdNaoEncontrado("Funcionário não encontrado com ID: "+id);
        }
        funcionarioRepository.deleteById(id);
    }

    public Funcionario readById(Long id){
        return funcionarioRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Funcionário não encontrado com ID: "+id));
    }
}
