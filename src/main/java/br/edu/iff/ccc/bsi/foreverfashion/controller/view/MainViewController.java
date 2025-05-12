package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping()
public class MainViewController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/home")
    public String getHome(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "home";
    }

    @GetMapping("/vendas")
    public String getVenda(Model model, HttpServletRequest request) {
        
        List<String> colunas = Arrays.asList("ID venda", "Valor", "Cliente", "Vendedor", "Data");
    
        List<List<String>> dados = Arrays.asList(
            Arrays.asList("1", "400,89", "João", "Marta", "28/04/2025"),
            Arrays.asList("2", "70,90", "Maria", "Fabiana", "28/04/2025"),
            Arrays.asList("3", "190,89", "Carlos", "Maria", "27/04/2025")
        );
    
        model.addAttribute("titulo", "Lista de Produtos");
        model.addAttribute("colunas", colunas);
        model.addAttribute("dados", dados);


        model.addAttribute("requestURI", request.getRequestURI());

        return "venda";
    }
    
    @GetMapping("/produtos")
    public String getProduto(Model model, HttpServletRequest request) {

        List<String> colunas = Arrays.asList("ID", "Descrição", "Valor");
    
        List<List<String>> dados = Arrays.asList(
            Arrays.asList("1", "Camisa esportiva", "134,99"),
            Arrays.asList("2", "Short", "70,90"),
            Arrays.asList("3", "Saia", "190,89")
        );
    
        model.addAttribute("titulo", "Lista de Produtos");
        model.addAttribute("colunas", colunas);
        model.addAttribute("dados", dados);

        model.addAttribute("requestURI", request.getRequestURI());

        return "produto";
    }

    @GetMapping("/funcionarios")
    public String getFuncionario(Model model, HttpServletRequest request) {
        List<String> colunas = Arrays.asList("ID", "Nome", "Cargo");
    
        List<List<String>> dados = Arrays.asList(
            Arrays.asList("1", "Marcela", "Caixa"),
            Arrays.asList("2", "Marta", "Vendedora"),
            Arrays.asList("3", "Maria", "Vendedora"),
            Arrays.asList("4","Fabiana", "Vendedora"),
            Arrays.asList("5", "Catarina", "Gerente")
        );
    
        model.addAttribute("titulo", "Lista de Funcionários");
        model.addAttribute("colunas", colunas);
        model.addAttribute("dados", dados);

        model.addAttribute("requestURI", request.getRequestURI());

        return "funcionario";
    }

    @GetMapping("/clientes")
    public String getCliente(Model model, HttpServletRequest request) {
        List<String> colunas = Arrays.asList("ID", "Nome", "CPF");
    
        List<List<String>> dados = Arrays.asList(
            Arrays.asList("1", "João", "111.111.111-11"),
            Arrays.asList("2", "Maria", "222.222.222-22"),
            Arrays.asList("3", "Carlos", "333.333.333-33")
        );
    
        model.addAttribute("titulo", "Lista de Clientes");
        model.addAttribute("colunas", colunas);
        model.addAttribute("dados", dados);

        model.addAttribute("requestURI", request.getRequestURI());

        return "cliente";
    }   

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        List<Usuario> usuarios = usuarioService.readAll();

        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(username) && u.getSenha().equals(password)) {
                //session.setAttribute("usuarioLogado", u);
                return "redirect:/home";
            }
        }

        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }
}
