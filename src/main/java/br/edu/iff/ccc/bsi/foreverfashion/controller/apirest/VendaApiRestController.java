package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest; 

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import br.edu.iff.ccc.bsi.foreverfashion.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaApiRestController {
    private final VendaService service;

    public VendaApiRestController(VendaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar uma nova venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venda cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Venda> create(@RequestBody Venda body) {
        Venda vendaCriada = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaCriada);
    }

    @Operation(summary = "Buscar todas as vendas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Vendas não encontradas", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Venda>> readAll() {
        List<Venda> vendas = service.readAll();
        if (!vendas.isEmpty()) {
            return ResponseEntity.ok(vendas);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar uma venda pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venda> readById(@PathVariable Long id) {
        Optional<Venda> venda = service.readById(id);
        if(venda.isPresent()){
            return ResponseEntity.ok(venda.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venda> update(@PathVariable Long id, @RequestBody Venda body) {
        Venda vendaAtualizada = service.update(id, body);
        return ResponseEntity.ok(vendaAtualizada);
    }

    @Operation(summary = "Deletar uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda deletada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean vendaDeletada = service.delete(id);
        if (!vendaDeletada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vendaDeletada);
    }
}
