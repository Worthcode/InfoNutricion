package com.redesnutricion.usuarios.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {

    @GetMapping("/")
    public String mostrarInicio() {
        return "inicio"; // Renderiza inicio.html

    }
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // login.html en templates
    }
}
