package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Funcionario;
import br.edu.iff.ccc.bsi.foreverfashion.service.FuncionarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("funcionarios")
public class FuncionarioViewController {
    private final FuncionarioService funcionarioService;

    @GetMapping()
    public String getCliente(Model model, HttpServletRequest request) {
        List<Funcionario> funcionarios = funcionarioService.readAll();
        
        List<List<String>> dados = new ArrayList<>();

        for(Funcionario funcionario : funcionarios) {
            List<String> linha = new ArrayList<>();
            linha.add(String.valueOf(funcionario.getId_pessoa()));
            linha.add(funcionario.getNome());
            linha.add(funcionario.getCargo().getDescricao());
            linha.add(funcionario.getEmail());
            linha.add(funcionario.getTelefone());
            linha.add(String.valueOf(funcionario.getSalario()));
            linha.add(funcionario.getEndereco().getEstado());
            linha.add(funcionario.getEndereco().getCidade());
            linha.add(funcionario.getEndereco().getCep());
            linha.add(funcionario.getEndereco().getBairro());
            linha.add(funcionario.getEndereco().getRua());
            linha.add(funcionario.getEndereco().getNumero());
            linha.add(funcionario.getEndereco().getComplemento());
            dados.add(linha);
        }

        model.addAttribute("dados", dados);
        model.addAttribute("titulo", "Lista de Funcionários");
        model.addAttribute("colunas", List.of("ID", "Nome", "Cargo","E-mail", "Telefone", "Salário", "Estado", "Cidade", "CEP", "Bairro", "Rua", "Número", "Complemento"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "funcionario";
    }
}
