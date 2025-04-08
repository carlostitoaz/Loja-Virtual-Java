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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import br.edu.iff.ccc.bsi.foreverfashion.entities.FormaPagamento;
import br.edu.iff.ccc.bsi.foreverfashion.service.FormaPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/forma-pagamentos")
public class FormaPagamentoApiRestController {
    private final FormaPagamentoService service;

    public FormaPagamentoApiRestController(FormaPagamentoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar uma nova forma de pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormaPagamento.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Forma de pagamento já cadastrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    public ResponseEntity<FormaPagamento> create(@RequestBody FormaPagamento body) {
        FormaPagamento formaPagamentoCriada = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoCriada);
    }

    @Operation(summary = "Buscar todas as formas de pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Formas de pagamento encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormaPagamento.class))),
        @ApiResponse(responseCode = "404", description = "Formas de pagamento não encontradas", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping()
    public ResponseEntity<List<FormaPagamento>> readAll() {
        List<FormaPagamento> formasPagamento = service.readAll();
        if (!formasPagamento.isEmpty()) {
            return ResponseEntity.ok(formasPagamento);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar uma forma de pagamento pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Forma de pagamento encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormaPagamento.class))),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> readById(@PathVariable Long id) {
        Optional<FormaPagamento> formaPagamento = service.readById(id);
        if (formaPagamento.isPresent()) {
            return ResponseEntity.ok(formaPagamento.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar uma forma de pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormaPagamento.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamento> update(@PathVariable Long id, @RequestBody FormaPagamento body) {
        FormaPagamento formaPagamentoAtualizada = service.update(id, body);
        return ResponseEntity.ok(formaPagamentoAtualizada);
    }

    @Operation(summary = "Deletar uma forma de pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Forma de pagamento deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean formaPagamentoDeletada = service.delete(id);
        if (!formaPagamentoDeletada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(formaPagamentoDeletada);
    }
}
