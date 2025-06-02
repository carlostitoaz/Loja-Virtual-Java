package br.edu.iff.ccc.bsi.foreverfashion.service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.*;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private CargoService cargoService;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private Cargo cargo;
    private Usuario usuario;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId_pessoa(1L);
        funcionario.setNome("João de Souza");
        funcionario.setEmail("joaosouza@gmail.com");
        funcionario.setTelefone("22999999992");
        funcionario.setSalario(1800.0);

        cargo = new Cargo();
        cargo.setId_cargo(1L);
        cargo.setDescricao("Atendente");
        funcionario.setCargo(cargo);

        endereco = new Endereco();
        endereco.setId_endereco(97L);
        endereco.setRua("Rua Boa Morte");
        endereco.setBairro("Parque das Dores");
        endereco.setCidade("Matinha");
        endereco.setEstado("Rio de Janeiro");
        endereco.setCep("12345672");
        endereco.setComplemento("Fundos");
        endereco.setNumero("9");
        funcionario.setEndereco(endereco);

        usuario = new Usuario();
        usuario.setId_usuario(33L);
        usuario.setUsuario("joaosouza");
        usuario.setSenha("123joaosouza");
        funcionario.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve criar funcionário com sucesso.")
    void deveCriarFuncionarioComSucesso() {
        when(funcionarioRepository.existsByEmail(funcionario.getEmail())).thenReturn(false);
        when(cargoService.readById(cargo.getId_cargo())).thenReturn(cargo);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.create(funcionario);

        assertEquals(funcionario, resultado);
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar funcionário com email já existente.")
    void deveLancarExcecaoAoCriarFuncionarioComEmailExistente() {
        when(funcionarioRepository.existsByEmail(funcionario.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> funcionarioService.create(funcionario));
        assertEquals("Funcionário já cadastrado com email fornecido.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve listar todos os funcionários.")
    void deveListarTodosFuncionarios() {
        when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));

        List<Funcionario> resultado = funcionarioService.readAll();

        assertEquals(1, resultado.size());
        assertEquals(funcionario, resultado.get(0));
    }

    @Test
    @DisplayName("Deve atualizar funcionário existente.")
    void deveAtualizarFuncionarioExistente() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(cargoService.readById(any())).thenReturn(cargo);
        when(pessoaService.findByEmail(funcionario.getEmail())).thenReturn(Optional.empty());
        when(pessoaService.findByTelefone(funcionario.getTelefone())).thenReturn(Optional.empty());
        when(usuarioService.findByUsuario(usuario.getUsuario())).thenReturn(Optional.empty());
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        Funcionario atualizado = funcionarioService.update(1L, funcionario);

        assertEquals(funcionario.getNome().toUpperCase().trim(), atualizado.getNome());
        verify(funcionarioRepository).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar funcionário inexistente.")
    void deveLancarExcecaoAoAtualizarFuncionarioInexistente() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IdNaoEncontrado.class, () -> funcionarioService.update(1L, funcionario));
    }

    @Test
    @DisplayName("Deve deletar funcionário existente.")
    void deveDeletarFuncionarioExistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(funcionarioRepository).deleteById(1L);

        funcionarioService.delete(1L);

        verify(funcionarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar funcionário inexistente.")
    void deveLancarExcecaoAoDeletarFuncionarioInexistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(false);

        assertThrows(IdNaoEncontrado.class, () -> funcionarioService.delete(1L));
    }

    @Test
    @DisplayName("Deve retornar funcionário por ID.")
    void deveRetornarFuncionarioPorId() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        Funcionario resultado = funcionarioService.readById(1L);

        assertEquals(funcionario, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar funcionário inexistente por ID.")
    void deveLancarExcecaoAoBuscarFuncionarioInexistentePorId() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IdNaoEncontrado.class, () -> funcionarioService.readById(1L));
    }
}
