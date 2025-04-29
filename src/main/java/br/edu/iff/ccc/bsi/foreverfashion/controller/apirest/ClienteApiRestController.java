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

import br.edu.iff.ccc.bsi.foreverfashion.dto.ClienteGetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.dto.ClienteSetDTO;
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
    public ResponseEntity<ClienteGetDTO> create(@RequestBody ClienteSetDTO body) {
        Cliente clienteCriado = service.create(body.transformaParaObjeto());
        ClienteGetDTO clienteGetDTO = new ClienteGetDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGetDTO.transformarParaClienteGetDTO(clienteCriado));
    }

    @Operation(summary = "Buscar todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Clientes não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<ClienteGetDTO>> readAll() {
        List<Cliente> clientes = service.readAll();
        if(!clientes.isEmpty()){
            ClienteGetDTO clienteGetDTO = new ClienteGetDTO();
            return ResponseEntity.ok(clienteGetDTO.transformarParaClienteGetDTO(clientes));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um cliente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> readOne(@PathVariable Long id) {
        Cliente cliente = service.readById(id);
        ClienteGetDTO clienteGetDTO = new ClienteGetDTO();
        return ResponseEntity.ok(clienteGetDTO.transformarParaClienteGetDTO(cliente));
    }

    @Operation(summary = "Atualizar um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> update(@PathVariable Long id, @RequestBody ClienteSetDTO body) {
        Cliente clienteAtualizado = service.update(id, body.transformaParaObjeto());
        ClienteGetDTO clienteGetDTO = new ClienteGetDTO();
        return ResponseEntity.ok(clienteGetDTO.transformarParaClienteGetDTO(clienteAtualizado));
    }

    @Operation(summary = "Deletar um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}