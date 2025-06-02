package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.AdministradorRepository;
import jakarta.transaction.Transactional;

@Service
public class AdministradorService {
    private final AdministradorRepository administradorRepository;  
    private final CargoService cargoService;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;

    public AdministradorService(AdministradorRepository administradorRepository, CargoService cargoService, PessoaService pessoaService, UsuarioService usuarioService) {
        this.administradorRepository = administradorRepository;
        this.cargoService = cargoService;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Administrador create(Administrador administrador) {
        if (administradorRepository.existsByDescricao(administrador.getDescricao())) {
            throw new JaCadastrado("Administrador já cadastrado com descrição fornecida.");
        }

        administrador.setCargo(cargoService.readById(administrador.getCargo().getId_cargo()));

        return administradorRepository.save(administrador);
    }

    public List<Administrador> readAll() {
        return administradorRepository.findAll();
    }   

    @Transactional
    public Administrador update(Long id, Administrador administrador) {
        Administrador administradorExistente = administradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Administrador não encontrado com ID "+id));

        administradorExistente.setDescricao(administrador.getDescricao());
        administradorExistente.setNome(administrador.getNome());
        administradorExistente.setCargo(cargoService.readById(administrador.getCargo().getId_cargo()));

        Optional<Pessoa> administradorEmailBuscado = pessoaService.findByEmail(administrador.getEmail());
        if(administradorEmailBuscado.isPresent() && !administradorEmailBuscado.get().getId_pessoa().equals(id)) {
            throw new JaCadastrado("Email já cadastrado.");
        }
        administradorExistente.setEmail(administrador.getEmail());
        
        Optional<Pessoa> administradorTelBuscado = pessoaService.findByTelefone(administrador.getTelefone());
        if(administradorTelBuscado.isPresent() && !administradorTelBuscado.get().getId_pessoa().equals(id)){
            throw new JaCadastrado("Telefone já cadastrado.");
        }
        administradorExistente.setTelefone(administrador.getTelefone());
        
        if(administrador.getUsuario().getUsuario() != null && administrador.getUsuario().getSenha() != null) {
            Optional<Usuario> usuarioResultado = usuarioService.findByUsuario(administrador.getUsuario().getUsuario());
            if(usuarioResultado.isPresent() && !usuarioResultado.get().getId_usuario().equals(administradorExistente.getUsuario().getId_usuario()) ){ 
                throw new JaCadastrado("Usuário já cadastrado.");
            }
            administradorExistente.getUsuario().setUsuario(administrador.getUsuario().getUsuario());
            administradorExistente.getUsuario().setSenha(administrador.getUsuario().getSenha());

        }else{
            throw new RuntimeException("Usuário ou senha não podem ser em branco.");
        }

        if (administrador.getEndereco() != null) {
            if (administradorExistente.getEndereco() == null) {
                administradorExistente.setEndereco(administrador.getEndereco());
            } else {
                administradorExistente.getEndereco().setRua(administrador.getEndereco().getRua());
                administradorExistente.getEndereco().setNumero(administrador.getEndereco().getNumero());
                administradorExistente.getEndereco().setBairro(administrador.getEndereco().getBairro());
                administradorExistente.getEndereco().setCidade(administrador.getEndereco().getCidade());
                administradorExistente.getEndereco().setEstado(administrador.getEndereco().getEstado());
                administradorExistente.getEndereco().setCep(administrador.getEndereco().getCep());
            }
        }
        return administradorRepository.save(administradorExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new IdNaoEncontrado("Administrador não encontrado com ID "+id);
        }
        administradorRepository.deleteById(id); 
    }

    public Administrador readById(Long id) {
        return administradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Administrador não encontrado com ID "+id));
    }   
}
