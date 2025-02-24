package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/venda")
public class VendaApiRestController {

    private final VendaService service;

    public VendaApiRestController(VendaService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar uma nova venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venda registrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Venda> create(
        @RequestBody Venda body,
        @RequestHeader Map<String, String> headers
    ) {
        Venda vendaCriada = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaCriada);
    }

    @Operation(summary = "Buscar todas as vendas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Nenhuma venda encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Venda>> readAll(@RequestHeader Map<String, String> headers) {
        List<Venda> vendas = service.readAll();
        return ResponseEntity.ok(vendas);
    }

    @Operation(summary = "Atualizar uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Venda> update(@RequestBody Venda body) {
        Venda vendaAtualizada = service.update(body);
        return ResponseEntity.ok(vendaAtualizada);
    }

    @Operation(summary = "Buscar venda por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Venda> readById(@PathVariable Long id) {
        Venda venda = service.findById(id);
        return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar uma venda por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
