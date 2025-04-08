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

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import br.edu.iff.ccc.bsi.foreverfashion.service.AdministradorService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorApiRestController {
    private final AdministradorService service;

    public AdministradorApiRestController(AdministradorService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Administrador já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Administrador> create(@RequestBody Administrador body) {
        Administrador administradorCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(administradorCriado);
    }

    @Operation(summary = "Buscar todos os administradores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administradores encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))),
        @ApiResponse(responseCode = "404", description = "Administradores não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Administrador>> readAll() {
        List<Administrador> administradores = service.readAll();
        if(!administradores.isEmpty()){
            return ResponseEntity.ok(administradores);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um administrador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> readById(@PathVariable Long id) {
        Optional<Administrador> administrador = service.readById(id);
        if(administrador.isPresent()){
            return ResponseEntity.ok(administrador.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar um administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> update(@PathVariable Long id, @RequestBody Administrador body) {
        Administrador administradorAtualizado = service.update(id, body);
        return ResponseEntity.ok(administradorAtualizado);
    }

    @Operation(summary = "Deletar um administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Administrador deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean administradorDeletado = service.delete(id);
        if(!administradorDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(administradorDeletado);
    }
}
