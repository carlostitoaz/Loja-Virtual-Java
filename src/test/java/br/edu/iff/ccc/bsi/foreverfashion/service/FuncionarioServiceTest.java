package br.edu.iff.ccc.bsi.foreverfashion.service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.*;
import br.edu.iff.ccc.bsi.foreverfashion.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();

        funcionario.setId_pessoa(1L);
        funcionario.setNome("João de Souza");
        funcionario.setEmail("joaosouza@gmail.com");
        funcionario.setTelefone("22999999992");
        funcionario.setSalario(1800.0);

        Cargo cargo = new Cargo();
        cargo.setId_cargo(1L);
        cargo.setDescricao("Atendente");
        funcionario.setCargo(cargo);

        Endereco endereco = new Endereco();
        endereco.setId_endereco(97L);
        endereco.setRua("Rua Boa Morte");
        endereco.setBairro("Parque das Dores");
        endereco.setCidade("Matinha");
        endereco.setEstado("Rio de Janeiro");
        endereco.setCep("12345672");
        endereco.setComplemento("Fundos");
        endereco.setNumero("9");
        funcionario.setEndereco(endereco);

        Usuario usuario = new Usuario();
        usuario.setId_usuario(33L);
        usuario.setUsuario("joaosouza");
        usuario.setSenha("123joaosouza");
        funcionario.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve criar funcionário com sucesso.")
    void deveCriarFuncionarioComSucesso() {
        when(funcionarioRepository.existsByEmail(funcionario.getEmail())).thenReturn(false);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.create(funcionario);

        assertEquals(funcionario, resultado);
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    @DisplayName("Deve listar todos os funcionários.")
    void deveListarTodosFuncionarios() {
        List<Funcionario> funcionarios = List.of(funcionario);

        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        List<Funcionario> resultado = funcionarioService.readAll();

        assertEquals(1, resultado.size());
        assertEquals(funcionarios.get(0), resultado.get(0));
    }

    @Test
    @DisplayName("Deve atualizar funcionario existente.")
    void deveAtualizarFuncionarioExistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(true);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario atualizado = funcionarioService.update(1L, funcionario);

        assertEquals(funcionario, atualizado);
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar funcionario inexistente.")
    void deveLancarExcecaoAoAtualizarFuncionarioInexistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            funcionarioService.update(1L, funcionario);
        });

        assertEquals("Funcionário não encontrado.", ex.getMessage());
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar funcionario existente.")
    void deveDeletarFuncionarioExistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(funcionarioRepository).deleteById(1L);

        boolean resultado = funcionarioService.delete(1L);

        assertTrue(resultado);
        verify(funcionarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve deletar funcionario inexistente.")
    void naoDeveDeletarFuncionarioInexistente() {
        when(funcionarioRepository.existsById(1L)).thenReturn(false);

        boolean resultado = funcionarioService.delete(1L);

        assertFalse(resultado);
        verify(funcionarioRepository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar funcionario por ID.")
    void deveRetornarFuncionarioPorId() {
        when(funcionarioRepository.existsById(1L)).thenReturn(true);
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        Optional<Funcionario> resultado = funcionarioService.readById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(funcionario, resultado.get());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar funcionario inexistente por ID.")
    void deveLancarExcecaoAoBuscarFuncionarioInexistentePorId() {
        when(funcionarioRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            funcionarioService.readById(1L);
        });

        assertEquals("Funcionário não encontrado.", ex.getMessage());
        verify(funcionarioRepository, never()).findById(any());
    }








}
