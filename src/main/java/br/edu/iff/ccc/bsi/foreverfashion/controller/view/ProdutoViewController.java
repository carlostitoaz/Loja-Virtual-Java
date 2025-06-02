package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Produto;
import br.edu.iff.ccc.bsi.foreverfashion.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("produtos")
public class ProdutoViewController {
    private final ProdutoService produtoService;

    @GetMapping()
    public String getProdutos(Model model, HttpServletRequest request) {
        List<Produto> produtos = produtoService.readAll();

        List<List<String>> dados = new ArrayList<>();

        for(Produto produto : produtos) {
            List<String> linha = new ArrayList<>();
            linha.add(String.valueOf(produto.getId_produto()));
            linha.add(produto.getDescricao());
            linha.add(String.valueOf(produto.getPreco_custo()));
            linha.add(String.valueOf(produto.getPreco_venda()));
            linha.add(String.valueOf(produto.getMax_desconto()));
            linha.add(String.valueOf(produto.getQuantidade()));
            linha.add(produto.getCor());
            linha.add(produto.getMarca());
            linha.add(produto.getMaterial());
            linha.add(produto.getTamanho());
            linha.add(produto.getCategoria().getDescricao());
            linha.add(String.valueOf(produto.getData_entrada()));
            dados.add(linha);
        }

        model.addAttribute("dados", dados);
        model.addAttribute("titulo", "Lista de Produtos");
        model.addAttribute("colunas", List.of("ID", "Descrição", "Preço de custo", "Preço de venda", "Desconto máximo", "Quantidade", "Cor", "Marca", "Material", "Tamanho", "Categoria", "Data de entrada"));
        model.addAttribute("requestURI", request.getRequestURI());

        return "produto";
    }
}