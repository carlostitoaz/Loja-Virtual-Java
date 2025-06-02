package br.edu.iff.ccc.bsi.foreverfashion.service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.*;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private FormaPagamentoService formaPagamentoService;

    @InjectMocks
    private VendaService vendaService;

    private Venda venda;

    @BeforeEach
    void setUp() {
        venda = new Venda();
        venda.setId_venda(1L);
        venda.setData(LocalDateTime.now());
        venda.setItens(new ArrayList<>());

        Cliente cliente = new Cliente();
        cliente.setId_cliente(1L);
        venda.setCliente(cliente);

        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(1L);
        venda.setPessoa(pessoa);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId_forma_pagamento(1L);
        venda.setForma_pagamento(formaPagamento);
    }

    @Test
    @DisplayName("Deve criar venda com sucesso.")
    void deveCriarVendaComSucesso() {
        when(clienteService.readById(anyLong())).thenReturn(venda.getCliente());
        when(pessoaService.readById(anyLong())).thenReturn(Optional.of(venda.getPessoa()));
        when(formaPagamentoService.readById(anyLong())).thenReturn(Optional.of(venda.getForma_pagamento()));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda resultado = vendaService.create(venda);

        assertNotNull(resultado);
        verify(vendaRepository).save(any(Venda.class));
    }

    @Test
    @DisplayName("Deve listar todas as vendas.")
    void deveListarTodasAsVendas() {
        when(vendaRepository.findAll()).thenReturn(List.of(venda));

        List<Venda> resultado = vendaService.readAll();

        assertEquals(1, resultado.size());
        verify(vendaRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar venda existente.")
    void deveAtualizarVendaExistente() {
        when(vendaRepository.findById(anyLong())).thenReturn(Optional.of(venda));
        when(clienteService.readById(anyLong())).thenReturn(venda.getCliente());
        when(pessoaService.readById(anyLong())).thenReturn(Optional.of(venda.getPessoa()));
        when(formaPagamentoService.readById(anyLong())).thenReturn(Optional.of(venda.getForma_pagamento()));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda atualizado = vendaService.update(1L, venda);

        assertNotNull(atualizado);
        verify(vendaRepository).save(any(Venda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar venda inexistente.")
    void deveLancarExcecaoQuandoVendaNaoExistir() {
        when(vendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        IdNaoEncontrado ex = assertThrows(IdNaoEncontrado.class, () -> vendaService.update(1L, venda));

        assertEquals("Venda não encontrada com ID 1", ex.getMessage());
    }

    @Test
    @DisplayName("Deve deletar venda existente.")
    void deveDeletarVendaExistente() {
        when(vendaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vendaRepository).deleteById(1L);

        vendaService.delete(1L);

        verify(vendaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar venda inexistente.")
    void naoDeveDeletarVendaInexistente() {
        when(vendaRepository.existsById(1L)).thenReturn(false);

        IdNaoEncontrado ex = assertThrows(IdNaoEncontrado.class, () -> vendaService.delete(1L));

        assertEquals("Venda não contrada com ID 1", ex.getMessage());
    }

    @Test
    @DisplayName("Deve buscar venda por ID com sucesso.")
    void deveRetornarVendaPorIdComSucesso() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Venda resultado = vendaService.readById(1L);

        assertEquals(venda, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar venda inexistente por ID.")
    void deveLancarExcecaoAoBuscarVendaInexistentePorId() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        IdNaoEncontrado ex = assertThrows(IdNaoEncontrado.class, () -> vendaService.readById(1L));

        assertEquals("Venda não encontrada com ID 1", ex.getMessage());
    }
}
