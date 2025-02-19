package br.edu.iff.ccc.bsi.foreverfashion.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping()
public class MainViewController {
    @GetMapping("/home")
    public String getHome() {
        return "home.html";
    }
}
