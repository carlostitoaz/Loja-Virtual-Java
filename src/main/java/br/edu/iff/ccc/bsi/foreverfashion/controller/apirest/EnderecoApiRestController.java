package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Endereco;
import br.edu.iff.ccc.bsi.foreverfashion.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoApiRestController {
    
    private final EnderecoService service;

    public EnderecoApiRestController(EnderecoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Endereco> create(@RequestBody Endereco body) {
        Endereco enderecoCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCriado);
    }

    @Operation(summary = "Buscar todos os endereços")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class))),
        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Endereco>> readAll() {
        List<Endereco> enderecos = service.readAll();
        if (!enderecos.isEmpty()) {
            return ResponseEntity.ok(enderecos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um endereço pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class))),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> readById(@PathVariable Long id) {
            Optional<Endereco> endereco = service.readById(id);
            if(endereco.isPresent()){
                return ResponseEntity.ok(endereco.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar um endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco body) {
        Endereco enderecoAtualizado = service.update(id, body);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @Operation(summary = "Deletar um endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean enderecoDeletado = service.delete(id);
        if (!enderecoDeletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enderecoDeletado);
    }
}
