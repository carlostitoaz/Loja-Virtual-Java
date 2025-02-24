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
@RequestMapping("/api/v1/usuario")
public class UsuarioApiRestController {

    private final UsuarioService service;

    public UsuarioApiRestController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Usuário já cadastrado", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> create(
        @RequestBody Usuario body,
        @RequestHeader Map<String, String> headers
    ) {
        Usuario usuarioCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(summary = "Buscar todos os usuários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuários não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/read-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Usuario>> readAll(@RequestHeader Map<String, String> headers) {
        List<Usuario> usuarios = service.readAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Usuario> update(@RequestBody Usuario body) {
        Usuario usuarioAtualizado = service.update(body);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}
