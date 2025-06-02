package br.edu.iff.ccc.bsi.foreverfashion.controller.apirest; 

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import br.edu.iff.ccc.bsi.foreverfashion.dto.VendaGetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.dto.VendaSetDTO;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import br.edu.iff.ccc.bsi.foreverfashion.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaApiRestController {
    private final VendaService service;

    public VendaApiRestController(VendaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar uma nova venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venda cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<VendaGetDTO> create(@RequestBody VendaSetDTO body) {
        Venda vendaCriada = service.create(body.transformaParaObjeto());
        VendaGetDTO vendaGetDTO = new VendaGetDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaGetDTO.transformaParaVendaDTO(vendaCriada));
    }

    @Operation(summary = "Buscar todas as vendas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Vendas não encontradas", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<VendaGetDTO>> readAll() {
        List<Venda> vendas = service.readAll();
        if (!vendas.isEmpty()) {
            VendaGetDTO vendaGetDTO = new VendaGetDTO();
            return ResponseEntity.ok(vendaGetDTO.transformaParaVendaDTO(vendas));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar uma venda pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendaGetDTO> readById(@PathVariable Long id) {
        Venda venda = service.readById(id);
        VendaGetDTO vendaGetDTO = new VendaGetDTO();
        return ResponseEntity.ok(vendaGetDTO.transformaParaVendaDTO(venda));     
    }

    @Operation(summary = "Atualizar uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<VendaGetDTO> update(@PathVariable Long id, @RequestBody VendaSetDTO body) {
        Venda vendaAtualizada = service.update(id, body.transformaParaObjeto());
        VendaGetDTO vendaGetDTO = new VendaGetDTO();
        return ResponseEntity.ok(vendaGetDTO.transformaParaVendaDTO(vendaAtualizada));
    }

    @Operation(summary = "Deletar uma venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda deletada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
