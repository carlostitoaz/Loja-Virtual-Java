package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest;

import java.util.List;

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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import br.edu.iff.ccc.bsi.foreverfashion.dto.CategoriaGetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.dto.CategoriaSetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;
import br.edu.iff.ccc.bsi.foreverfashion.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaApiRestController {
    private final CategoriaService service;

    public CategoriaApiRestController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar uma nova categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Categoria já cadastrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<CategoriaGetDTO> create(@RequestBody CategoriaSetDTO body) {
        Categoria categoriaCriada = service.create(body.transformarParaObjeto());
        CategoriaGetDTO categoriaGetDTO = new CategoriaGetDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGetDTO.transformaParaCategoriaDTO(categoriaCriada));
    }

    @Operation(summary = "Buscar todas as categorias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categorias não encontradas", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<CategoriaGetDTO>> readAll() {
        List<Categoria> categorias = service.readAll();
        if(!categorias.isEmpty()){
            CategoriaGetDTO categoriaGetDTO = new CategoriaGetDTO();
            return ResponseEntity.ok(categoriaGetDTO.transformaParaCategoriaDTO(categorias));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar uma categoria pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaGetDTO> readById(@PathVariable Long id) {
        Categoria categoria = service.readById(id);
        CategoriaGetDTO categoriaGetDTO = new CategoriaGetDTO();
        return ResponseEntity.ok(categoriaGetDTO.transformaParaCategoriaDTO(categoria));
    }

    @Operation(summary = "Atualizar uma categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGetDTO> update(@PathVariable Long id, @RequestBody CategoriaSetDTO body) {
        Categoria categoriaAtualizada = service.update(id, body.transformarParaObjeto());
        CategoriaGetDTO categoriaGetDTO = new CategoriaGetDTO();
        return ResponseEntity.ok(categoriaGetDTO.transformaParaCategoriaDTO(categoriaAtualizada));
    }

    @Operation(summary = "Deletar uma categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso", content = @Content(mediaType =  "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
