package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Venda;
import br.edu.iff.ccc.bsi.foreverfashion.service.VendaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("vendas")
public class VendaViewController {
    private final VendaService vendaService;

    @GetMapping()
    public String getVendas(Model model, HttpServletRequest request) {
        List<Venda> vendas = vendaService.readAll();
        List<List<String>> dados = new ArrayList<>();

        DateTimeFormatter dataVenda = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaVenda = DateTimeFormatter.ofPattern("HH:mm:ss");

        for(Venda venda : vendas) {
            List<String> linha = new ArrayList<>();
            linha.add(String.valueOf(venda.getId_venda()));
            linha.add(venda.getCliente().getNome());
            linha.add(venda.getPessoa().getNome());
            linha.add(String.valueOf(venda.getValor_total()));
            linha.add(String.valueOf(venda.getDesconto()));
            linha.add(String.valueOf(venda.getData().format(dataVenda)));
            linha.add(String.valueOf(venda.getData().format(horaVenda)));
            dados.add(linha);
        }

        model.addAttribute("titulo", "Lista de vendas");
        model.addAttribute("colunas", List.of("ID", "Cliente", "Vendedor","Total", "Desconto", "Data","Hora"));
        model.addAttribute("dados", dados);
        model.addAttribute("requestURI", request.getRequestURI());

        return "venda";
    }
}