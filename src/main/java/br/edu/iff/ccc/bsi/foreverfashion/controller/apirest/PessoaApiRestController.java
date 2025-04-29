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
import java.util.Optional;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.service.*;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaApiRestController {
    private final PessoaService service;

    public PessoaApiRestController(PessoaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar uma nova pessoa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Pessoa já cadastrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa body) {
        Pessoa pessoaCriada = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @Operation(summary = "Buscar todas as pessoas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "404", description = "Pessoas não encontradas", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Pessoa>> readAll() {
        List<Pessoa> pessoas = service.readAll();
        if(!pessoas.isEmpty()){
            return ResponseEntity.ok(pessoas);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar uma pessoa pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> readOne(@PathVariable Long id) {
        Optional<Pessoa> pessoa = service.readById(id);
        if(pessoa.isPresent()){
            return ResponseEntity.ok(pessoa.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar uma pessoa")   
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa body) {
        Pessoa pessoaAtualizada = service.update(id, body);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @Operation(summary = "Deletar uma pessoa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean pessoaDeletada = service.delete(id);
        if(pessoaDeletada){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
    }
}
