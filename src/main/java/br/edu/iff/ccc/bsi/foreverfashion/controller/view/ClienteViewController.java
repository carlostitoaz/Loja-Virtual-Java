package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cliente;
import br.edu.iff.ccc.bsi.foreverfashion.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("clientes")
public class ClienteViewController {
    private final ClienteService clienteService;

    @GetMapping()
    public String getClientes(Model model, HttpServletRequest request) {
        List<Cliente> clientes = clienteService.readAll();
        
        List<List<String>> dados = new ArrayList<>();

        for(Cliente cliente : clientes){
            List<String> linha = new ArrayList<>();
            linha.add(String.valueOf(cliente.getId_cliente()));
            linha.add(cliente.getNome());
            linha.add(cliente.getCpf());
            dados.add(linha);
        }

        model.addAttribute("dados", dados);

        model.addAttribute("titulo", "Lista de Clientes");
        model.addAttribute("colunas", List.of("ID", "Nome", "CPF"));

        model.addAttribute("requestURI", request.getRequestURI());

        return "cliente";
    }

}