package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class VendaGetDTO {
    private Long id_venda;
    private LocalDateTime data;
    private List<Long> id_item;
    private Long  id_cliente;
    private Long id_pessoa;
    private Long id_forma_pagamento;
    private Double desconto;
    private Double totalVenda;

    public VendaGetDTO transformaParaVendaDTO(Venda venda) {
        VendaGetDTO vendaGetDTO = new VendaGetDTO();

        vendaGetDTO.setId_venda(venda.getId_venda());
        vendaGetDTO.setData(venda.getData());
        vendaGetDTO.setId_item(venda.getItens().stream().map(ItemVenda::getId_item_venda).toList());
        vendaGetDTO.setId_cliente(venda.getCliente().getId_cliente());
        vendaGetDTO.setId_pessoa(venda.getPessoa().getId_pessoa());
        vendaGetDTO.setId_forma_pagamento(venda.getForma_pagamento().getId_forma_pagamento());
        vendaGetDTO.setDesconto(venda.getDesconto());
        vendaGetDTO.setTotalVenda(venda.getValor_total());
    
        return vendaGetDTO;
    }

    public List<VendaGetDTO> transformaParaVendaDTO(List<Venda> vendas) {
        List<VendaGetDTO> vendaGetDTO = new ArrayList<>();

        for(Venda venda : vendas) {
            vendaGetDTO.add(transformaParaVendaDTO(venda));
        }
        return vendaGetDTO;
    }
}