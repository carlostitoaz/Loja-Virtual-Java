package br.edu.iff.ccc.bsi.foreverfashion.dto;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteSetDTO {
    private String nome;
    private String cpf;

    public Cliente transformaParaObjeto() {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        return cliente;
    }
}
