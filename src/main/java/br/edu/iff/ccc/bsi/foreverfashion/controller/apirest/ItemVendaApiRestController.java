package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest; 

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.service.ItemVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/item-vendas")
public class ItemVendaApiRestController {
    private final ItemVendaService service;

    public ItemVendaApiRestController(ItemVendaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo item de venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "ItemVenda cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<ItemVenda> create(@RequestBody ItemVenda body) {
        ItemVenda itemCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCriado);
    }

    @Operation(summary = "Buscar todos os itens de venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Itens de venda encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "404", description = "Itens de venda não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<ItemVenda>> readAll() {
        List<ItemVenda> itens = service.readAll();
        if (!itens.isEmpty()) {
            return ResponseEntity.ok(itens);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um item de venda pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item de venda encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "404", description = "Item de venda não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> readById(@PathVariable Long id) {
        Optional<ItemVenda> item = service.readById(id);
        if(item.isPresent()){
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar um item de venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item de venda atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> update(@PathVariable Long id, @RequestBody ItemVenda body) {
        ItemVenda itemAtualizado = service.update(id, body);
        return ResponseEntity.ok(itemAtualizado);
    }

    @Operation(summary = "Deletar um item de venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item de venda deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "404", description = "Item de venda não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean itemDeletado = service.delete(id);
        if (!itemDeletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemDeletado);
    }
}
