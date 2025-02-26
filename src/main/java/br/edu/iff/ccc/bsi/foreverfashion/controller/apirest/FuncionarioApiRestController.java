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
@RequestMapping("/api/v1/funcionario")
public class FuncionarioApiRestController {

    private final FuncionarioService service;

    public FuncionarioApiRestController(FuncionarioService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Funcionario> create(
        @RequestBody Funcionario body,
        @RequestHeader Map<String, String> headers
    ) {
        Funcionario funcionarioCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }

    @Operation(summary = "Buscar todos os funcionários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionários encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "404", description = "Nenhum funcionário encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Funcionario>> readAll(@RequestHeader Map<String, String> headers) {
        List<Funcionario> funcionarios = service.readAll();
        return ResponseEntity.ok(funcionarios);
    }

    @Operation(summary = "Atualizar um funcionário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Funcionario> update(@RequestBody Funcionario body) {
        Funcionario funcionarioAtualizado = service.update(body);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Funcionario.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Funcionario> readById(@PathVariable Long id) {
        Funcionario funcionario = service.findById(id);
        return funcionario != null ? ResponseEntity.ok(funcionario) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar um funcionário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Funcionário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
