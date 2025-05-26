package com.redesnutricion.usuarios.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioControlador {

    @GetMapping("/")
    public String mostrarInicio(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("mensajeLogout", "Sesión cerrada exitosamente.");
        }
        return "inicio"; // Renderiza inicio.html
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // login.html en templates
    }
}
