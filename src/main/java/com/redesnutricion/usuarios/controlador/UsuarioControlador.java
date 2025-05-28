package com.redesnutricion.usuarios.controlador;

import com.redesnutricion.analizador.dtoanalisis.EnlaceDto;
import com.redesnutricion.usuarios.dto.UsuarioRegistroDTO;
import com.redesnutricion.usuarios.modelo.TokenVerificacion;
import com.redesnutricion.usuarios.modelo.Usuario;
import com.redesnutricion.usuarios.repositorio.TokenRepositorio;
import com.redesnutricion.usuarios.repositorio.UsuarioRepositorio;
import com.redesnutricion.usuarios.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {
    @Autowired
    private TokenRepositorio tokenRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model modelo) {
        modelo.addAttribute("usuario", new UsuarioRegistroDTO());
        return "registro";  // src/main/resources/templates/registro.html
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO dto, Model modelo) {
        try {
            usuarioServicio.guardar(dto);
            return "redirect:/usuarios/registro?exito";
        } catch (IllegalArgumentException e) {
            modelo.addAttribute("usuario", dto);
            modelo.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/lista")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.obtenerTodos());
        modelo.addAttribute("enlaceDto", new EnlaceDto()); // <- Añade esto
        return "lista";
    }


    @GetMapping("/validar")
    public String validarCuenta(@RequestParam("token") String token) {
        TokenVerificacion verificacion = tokenRepositorio.findByToken(token);
        if (verificacion == null || verificacion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return "redirect:/usuarios/registro?invalido";
        }

        Usuario usuario = verificacion.getUsuario();
        usuario.setVerificado(true);
        usuarioRepositorio.save(usuario);

        tokenRepositorio.delete(verificacion);

        return "redirect:/usuarios/registro?verificado";
    }


}



