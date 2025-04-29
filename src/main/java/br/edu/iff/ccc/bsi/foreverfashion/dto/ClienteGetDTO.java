package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteGetDTO {
    private Long id_cliente;
    private String nome;
    private String cpf;

    public ClienteGetDTO transformarParaClienteGetDTO(Cliente cliente) {
        ClienteGetDTO clienteGetDTO = new ClienteGetDTO();
        clienteGetDTO.setId_cliente(cliente.getId_cliente());
        clienteGetDTO.setNome(cliente.getNome());
        clienteGetDTO.setCpf(cliente.getCpf());
        return clienteGetDTO;
    }

    public List<ClienteGetDTO> transformarParaClienteGetDTO(List<Cliente> clientes){
        List<ClienteGetDTO> clienteGetDTO = new ArrayList<>();
        for(Cliente cliente : clientes){
            clienteGetDTO.add(transformarParaClienteGetDTO(cliente));
        }
        return clienteGetDTO;
    }
}
