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
@RequestMapping("/api/v1/produto")
public class ProdutoApiRestController {

    private final ProdutoService service;

    public ProdutoApiRestController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Produto já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> create(
        @RequestBody Produto body,
        @RequestHeader Map<String, String> headers
    ) {
        Produto produtoCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @Operation(summary = "Buscar todos os produtos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Produto>> readAll(@RequestHeader Map<String, String> headers) {
        List<Produto> produtos = service.readAll();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Atualizar um produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Produto> update(@RequestBody Produto body) {
        Produto produtoAtualizado = service.update(body);
        return ResponseEntity.ok(produtoAtualizado);
    }
}
