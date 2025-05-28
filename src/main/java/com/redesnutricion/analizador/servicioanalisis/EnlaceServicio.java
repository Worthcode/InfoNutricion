package com.redesnutricion.analizador.servicioanalisis;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class EnlaceServicio {

    private static final List<String> PALABRAS_CLAVE_CONFIABLES = Arrays.asList(
            "estudio", "científico", "evidencia", "universidad", "organización mundial", "pubmed", "revista médica"
    );

    private static final List<String> ALERTAS_NO_CONFIABLE = Arrays.asList(
            "milagro", "sin esfuerzo", "cura rápida", "secreto", "100% garantizado", "antes y después"
    );

    public String evaluar(String url) {
        try {
            String contenido = obtenerContenidoWeb(url).toLowerCase();

            boolean confiable = PALABRAS_CLAVE_CONFIABLES.stream().anyMatch(contenido::contains);
            boolean alerta = ALERTAS_NO_CONFIABLE.stream().anyMatch(contenido::contains);

            if (confiable && !alerta) {
                return "✅ El contenido parece confiable.";
            } else if (alerta && !confiable) {
                return "⚠️ El contenido parece no confiable. Usa fuentes verificadas.";
            } else {
                return "ℹ️ No se puede determinar claramente. Revisa la fuente con precaución.";
            }

        } catch (Exception e) {
            return "❌ Error al analizar el enlace. Asegúrate de que sea válido.";
        }
    }

    private String obtenerContenidoWeb(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder contenido = new StringBuilder();
        String linea;

        while ((linea = in.readLine()) != null) {
            contenido.append(linea);
        }

        in.close();
        return contenido.toString();
    }
}
