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
@RequestMapping("/api/v1/cliente")
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
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> create(
        @RequestBody Cliente body,
        @RequestHeader Map<String, String> headers
    ) {
        Cliente clienteCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    @Operation(summary = "Buscar todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Clientes não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cliente>> readAll(@RequestHeader Map<String, String> headers) {
        List<Cliente> clientes = service.readAll();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Atualizar um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> update(@RequestBody Cliente body) {
        Cliente clienteAtualizado = service.update(body);
        return ResponseEntity.ok(clienteAtualizado);
    }
}
