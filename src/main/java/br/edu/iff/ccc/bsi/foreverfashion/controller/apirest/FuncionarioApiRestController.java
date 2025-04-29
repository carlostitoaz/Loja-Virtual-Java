package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Funcionario;
import br.edu.iff.ccc.bsi.foreverfashion.service.FuncionarioService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioApiRestController {
    private final FuncionarioService service;

    public FuncionarioApiRestController(FuncionarioService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Funcionário já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Funcionario> create(@RequestBody Funcionario body) {
        Funcionario funcionarioCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }

    @Operation(summary = "Buscar todos os funcionários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionários encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "404", description = "Funcionários não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Funcionario>> readAll() {
        List<Funcionario> funcionarios = service.readAll();
        if(!funcionarios.isEmpty()){
            return ResponseEntity.ok(funcionarios);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um funcionário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> readById(@PathVariable Long id) {
        Optional<Funcionario> funcionario = service.readById(id);
        if(funcionario.isPresent()){
            return ResponseEntity.ok(funcionario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar um funcionário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody Funcionario body) {
        Funcionario funcionarioAtualizado = service.update(id, body);
        return ResponseEntity.ok(funcionarioAtualizado);
    }
    
    @Operation(summary = "Deletar um funcionário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário deletado com sucesso", content = @Content(mediaType =  "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean funcionarioDeletado = service.delete(id);
        if(!funcionarioDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarioDeletado);
    }
}