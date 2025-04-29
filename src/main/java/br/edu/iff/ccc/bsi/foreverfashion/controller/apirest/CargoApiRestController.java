package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import br.edu.iff.ccc.bsi.foreverfashion.dto.CargoGetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.dto.CargoSetDTO;
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
    public ResponseEntity<CargoGetDTO> create(@RequestBody CargoSetDTO body) {
        Cargo cargoCriado = service.create(body.transformaParaObjeto());
        CargoGetDTO cargosGetDTO = new CargoGetDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(cargosGetDTO.transformaParaCargoDTO(cargoCriado));
    }

    @Operation(summary = "Buscar todos os cargos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargos não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<CargoGetDTO>> readAll() {
        List<Cargo> cargos = service.readAll();
        if (!cargos.isEmpty()) {
            CargoGetDTO cargoGetDTO = new CargoGetDTO();
            return ResponseEntity.ok(cargoGetDTO.transformaParaCargoDTO(cargos));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um cargo pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CargoGetDTO> readById(@PathVariable Long id) {
        Cargo cargo = service.readById(id);
        CargoGetDTO cargoGetDTO = new CargoGetDTO();
        return ResponseEntity.ok(cargoGetDTO.transformaParaCargoDTO(cargo));
    }

    @Operation(summary = "Atualizar um cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CargoGetDTO> update(@PathVariable Long id, @RequestBody CargoSetDTO body) {
        Cargo cargoAtualizado = service.update(id, body.transformaParaObjeto());
        CargoGetDTO cargoGetDTO = new CargoGetDTO();
        return ResponseEntity.ok(cargoGetDTO.transformaParaCargoDTO(cargoAtualizado));
    }

    @Operation(summary = "Deletar um cargo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cargo deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cargo.class))),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
