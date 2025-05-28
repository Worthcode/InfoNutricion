package com.redesnutricion.analizador.controladoranalisis;

import com.redesnutricion.analizador.dtoanalisis.EnlaceDto;
import com.redesnutricion.analizador.servicioanalisis.EnlaceServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnlaceControlador {

    @Autowired
    private EnlaceServicio servicio;

    @PostMapping("/evaluar")
    public String evaluar(@ModelAttribute EnlaceDto enlaceDto, RedirectAttributes redirectAttributes) {
        String resultado = servicio.evaluar(enlaceDto.getUrl());
        redirectAttributes.addFlashAttribute("resultado", resultado);
        return "redirect:/usuarios/lista";
    }
}
