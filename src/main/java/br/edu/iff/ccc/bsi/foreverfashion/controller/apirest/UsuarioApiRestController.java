package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
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
    @PostMapping()
    public ResponseEntity<Usuario> create(@RequestBody Usuario body) {
        Usuario usuarioCriado = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(summary = "Buscar todos os usuários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuários não encontrados", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Usuario>> readAll() {
        List<Usuario> usuarios = service.readAll();
        if(!usuarios.isEmpty()){
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar um usuario pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content(schema = @Schema(implementation = Error.class)))
    
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> readById(@PathVariable Long id) {
        Optional<Usuario> usuario = service.readById(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario body) {
        Usuario usuarioAtualizado = service.update(id, body);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(summary = "Deletar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletada com sucesso", content = @Content(mediaType =  "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        boolean usuarioDeletado = service.delete(id);
        if(!usuarioDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDeletado);
    }
}