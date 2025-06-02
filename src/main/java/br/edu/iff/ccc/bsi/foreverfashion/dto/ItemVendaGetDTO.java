package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemVendaGetDTO {
    private Long id_item_venda;
    private int quantidade;
    private Long id_produto;
    private Double desconto;
    private Double total;

    public ItemVendaGetDTO transformarParaItemVendaGetDTO(ItemVenda itemVenda) {
        ItemVendaGetDTO itemVendaGetDTO = new ItemVendaGetDTO();
        itemVendaGetDTO.setId_item_venda(itemVenda.getId_item_venda());
        itemVendaGetDTO.setQuantidade(itemVenda.getQuantidade());
        itemVendaGetDTO.setId_produto(itemVenda.getProduto().getId_produto());
        itemVendaGetDTO.setDesconto(itemVenda.getDesconto());
        itemVendaGetDTO.setTotal(itemVenda.getValor_total());

        return itemVendaGetDTO;
    }

    public List<ItemVendaGetDTO> transformarParaItemVendaGetDTO (List<ItemVenda> itemVendas) {
        List<ItemVendaGetDTO> itemVendaGetDTO = new ArrayList<>();
        for(ItemVenda itemVenda : itemVendas) {
            itemVendaGetDTO.add(transformarParaItemVendaGetDTO(itemVenda));
        }
        return itemVendaGetDTO;
    }

}
