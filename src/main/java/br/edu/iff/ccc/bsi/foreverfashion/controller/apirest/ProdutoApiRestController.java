package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import br.edu.iff.ccc.bsi.foreverfashion.service.ProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produtos")
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
    @PostMapping()
    public ResponseEntity<Produto> create(@RequestBody Produto body) {
        Produto produtoCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @Operation(summary = "Buscar todos os produtos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Produto>> readAll() {
        List<Produto> produtos = service.readAll();
        if(!produtos.isEmpty()){
            return ResponseEntity.ok(produtos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um produto pelo ID")  
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })  
    @GetMapping("/{id}")
    public ResponseEntity<Produto> readById(@PathVariable Long id) {
        Optional<Produto> produto = service.readById(id);
        if(produto.isPresent()){
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar um produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto body) {
        Produto produtoAtualizado = service.update(id, body);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Deletar um produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean produtoDeletado = service.delete(id);
        if(!produtoDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoDeletado);
    }
}