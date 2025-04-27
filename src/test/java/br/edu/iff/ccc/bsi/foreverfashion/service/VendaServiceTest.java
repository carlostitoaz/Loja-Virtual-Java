package br.edu.iff.ccc.bsi.foreverfashion.service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    private Venda venda;

    @BeforeEach
    void setUp() {
        venda = new Venda();
        venda.setId_venda(1L);
        venda.setData(LocalDateTime.now());
        venda.setItens(new ArrayList<>());
        venda.setCliente(new Cliente());
        venda.setPessoa(new Pessoa());
        venda.setForma_pagamento(new FormaPagamento());
    }

    @Test
    @DisplayName("Deve criar venda com sucesso.")
    void deveCriarVendaComSucesso() {
        when(vendaRepository.save(venda)).thenReturn(venda);

        Venda resultado = vendaService.create(venda);

        assertEquals(venda, resultado);
        verify(vendaRepository).save(venda);
    }

    @Test
    @DisplayName("Deve listar todas as vendas.")
    void deveListarTodasAsVendas() {
        List<Venda> vendas = List.of(venda);
        when(vendaRepository.findAll()).thenReturn(vendas);

        List<Venda> resultado = vendaService.readAll();

        assertEquals(vendas, resultado);
        verify(vendaRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar vendas existentes.")
    void deveAtualizarVendaExistente() {
        when(vendaRepository.existsById(venda.getId_venda())).thenReturn(true);
        when(vendaRepository.save(venda)).thenReturn(venda);

        Venda atualizado = vendaService.update(1L, venda);

        assertEquals(venda, atualizado);
        verify(vendaRepository).save(venda);
    }

    @Test
    @DisplayName("Deve lançar exceção quando venda não existir.")   
    void deveLancarExcecaoQuandoVendaNaoExistir() {
        when(vendaRepository.existsById(venda.getId_venda())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            vendaService.update(1L, venda);
        });

        assertEquals("Venda não encontrada.", ex.getMessage());
        verify(vendaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar venda existente.")
    void deveDeletarVendaExistente() {
        when(vendaRepository.existsById(venda.getId_venda())).thenReturn(true);
        doNothing().when(vendaRepository).deleteById(venda.getId_venda());

        boolean resultado = vendaService.delete(1L);

        assertTrue(resultado);
        verify(vendaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve deletar venda inexistente.")
    void naoDeveDeletarVendaInexistente() {
        when(vendaRepository.existsById(venda.getId_venda())).thenReturn(false);

        boolean resultado = vendaService.delete(1L);

        assertFalse(resultado);
        verify(vendaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve buscar venda por ID com sucesso.")
    void deveRetornarVendaPorIdComSucesso() {
        when(vendaRepository.existsById(1L)).thenReturn(true);
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Optional<Venda> resultado = vendaService.readById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(venda, resultado.get());
    }

    @Test
    @DisplayName("Deve lançar exceção ao busca venda inexistente por ID.")
    void deveLancarExcecaoAoBuscarVendaInexistentePorId() {
        when(vendaRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            vendaService.readById(1L);
        });

        assertEquals("Venda não encontrada.", ex.getMessage());
        verify(vendaRepository, never()).findById(any());   
    }






















}
