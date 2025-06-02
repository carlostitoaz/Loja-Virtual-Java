package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.entities.FormaPagamento;
import br.edu.iff.ccc.bsi.foreverfashion.entities.ItemVenda;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class VendaSetDTO {
    private List<ItemVendaSetDTO> itens = new ArrayList<>();
    private Long id_cliente;
    private Long id_pessoa;
    private Long id_forma_pagamento;
    //private Double desconto;

    public Venda transformaParaObjeto() {
        Venda venda = new Venda();

        List<ItemVenda> itensVenda = new ArrayList<>();

        for (ItemVendaSetDTO item : itens) {
            itensVenda.add(item.transformaParaObjeto());
        }

        venda.setItens(itensVenda);

        Cliente cliente = new Cliente();
        cliente.setId_cliente(id_cliente);
        venda.setCliente(cliente);

        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(id_pessoa);
        venda.setPessoa(pessoa);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId_forma_pagamento(id_forma_pagamento);
        venda.setForma_pagamento(formaPagamento);

        //venda.setDesconto(desconto);

        return venda;
    }
}
