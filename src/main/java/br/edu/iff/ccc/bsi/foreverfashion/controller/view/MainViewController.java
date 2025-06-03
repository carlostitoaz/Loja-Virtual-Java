package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import java.util.Optional;

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

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    /* 
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        
        Optional<Usuario> usuarioBuscado = usuarioService.findByUsuarioAndSenha(username, password);
                
        if (usuarioBuscado.isPresent()) {
            session.setAttribute("usuarioLogado", usuarioBuscado);
            return "redirect:/home";
        }

        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }
        */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
