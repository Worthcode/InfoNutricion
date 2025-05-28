package com.redesnutricion.analizador.utilanalisis;

import org.springframework.stereotype.Component;

@Component
public class VerificadorConfiabilidad {

    public String analizar(String url) {
        if (url.contains("wikipedia") || url.contains("who.int") || url.contains("fao.org")) {
            return "El enlace parece confiable.";
        } else {
            return "El enlace no se puede verificar como confiable.";
        }
    }
}
