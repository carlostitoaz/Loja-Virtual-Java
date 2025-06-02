package br.edu.iff.ccc.bsi.foreverfashion.dto;

import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemVendaSetDTO {
    private int quantidade;
    private Double desconto;
    private Long id_produto;

    public ItemVenda transformaParaObjeto() {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(quantidade);
        itemVenda.setDesconto(desconto);

        Produto produto = new Produto();
        produto.setId_produto(id_produto);
        itemVenda.setProduto(produto);

        return itemVenda;
    }
}
