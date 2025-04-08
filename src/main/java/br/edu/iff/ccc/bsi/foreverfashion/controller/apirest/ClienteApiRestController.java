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
import java.util.Optional;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.service.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteApiRestController {

    private final ClienteService service;

    public ClienteApiRestController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Cliente já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<Cliente> create(@RequestBody Cliente body) {
        Cliente clienteCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    @Operation(summary = "Buscar todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Clientes não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Cliente>> readAll() {
        List<Cliente> clientes = service.readAll();
        if(!clientes.isEmpty()){
            return ResponseEntity.ok(clientes);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um cliente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> readOne(@PathVariable Long id) {
        Optional<Cliente> cliente = service.readById(id);
        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Atualizar um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente body) {
        Cliente clienteAtualizado = service.update(id, body);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @Operation(summary = "Deletar um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean clienteDeletado = service.delete(id);
        if(!clienteDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDeletado);
    }
}