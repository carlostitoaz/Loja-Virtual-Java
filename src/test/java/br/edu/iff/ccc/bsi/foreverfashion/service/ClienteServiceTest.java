package br.edu.iff.ccc.bsi.foreverfashion.service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("Maria");
        cliente.setCpf("12345678901");
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso.")
    void deveCriarClienteComSucesso() {
        when(clienteRepository.existsByCpf(cliente.getCpf())).thenReturn(false);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.create(cliente);

        assertEquals(cliente, resultado);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existir.")
    void deveLancarExcecaoQuandoCpfJaExistir() {
        when(clienteRepository.existsByCpf(cliente.getCpf())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.create(cliente);
        });

        assertEquals("Cliente já cadastrado com CPF fornecido.", ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os clientes.")
    void deveListarTodosClientes() {
        List<Cliente> clientes = List.of(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.readAll();

        assertEquals(1, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar clientes existentes.")
    void deveAtualizarClienteExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente atualizado = clienteService.update(1L, cliente);

        assertEquals(cliente, atualizado);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar cliente inexistente.")
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.update(1L, cliente);
        });

        assertEquals("Cliente não encontrado com ID: "+1L, ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar cliente existente.")
    void deveDeletarClienteExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.delete(1L);

        verify(clienteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve deletar cliente inexistente.")
    void naoDeveDeletarClienteInexistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.delete(1L);
        });

        assertEquals("Cliente não encontrado com ID: "+1L, ex.getMessage());
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve buscar cliente por ID com sucesso.")
    void deveBuscarClientePorIdComSucesso() {
        //when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.readById(1L);

        assertEquals(cliente, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente por ID.")
    void deveLancarExcecaoAoBuscarClienteInexistentePorId() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.readById(1L);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
        verify(clienteRepository, never()).findById(any());
    }
}
