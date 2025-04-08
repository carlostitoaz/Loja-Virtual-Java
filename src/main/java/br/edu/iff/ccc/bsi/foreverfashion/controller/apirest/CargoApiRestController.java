package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
import br.edu.iff.ccc.bsi.foreverfashion.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/cargos")
public class CargoApiRestController {
    private final CargoService service;

    public CargoApiRestController(CargoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cargo cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Cargo já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Cargo> create(@RequestBody Cargo body) {
        Cargo cargoCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargoCriado);
    }

    @Operation(summary = "Buscar todos os cargos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargos não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Cargo>> readAll() {
        List<Cargo> cargos = service.readAll();
        if (!cargos.isEmpty()) {
            return ResponseEntity.ok(cargos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um cargo pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> readById(@PathVariable Long id) {
        Optional<Cargo> cargo = service.readById(id);
        if(cargo.isPresent()){
            return ResponseEntity.ok(cargo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar um cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cargo> update(@PathVariable Long id, @RequestBody Cargo body) {
        Cargo cargoAtualizado = service.update(id, body);
        return ResponseEntity.ok(cargoAtualizado);
    }

    @Operation(summary = "Deletar um cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean cargoDeletado = service.delete(id);
        if (!cargoDeletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cargoDeletado);
    }
}
