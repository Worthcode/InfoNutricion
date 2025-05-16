package com.redesnutricion.usuarios.servicio;

import com.redesnutricion.usuarios.dto.UsuarioRegistroDTO;
import com.redesnutricion.usuarios.modelo.TokenVerificacion;
import com.redesnutricion.usuarios.modelo.Usuario;
import com.redesnutricion.usuarios.repositorio.TokenRepositorio;
import com.redesnutricion.usuarios.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenRepositorio tokenRepositorio;

    @Autowired
    private CorreoServicio correoServicio;
    public Usuario guardar(UsuarioRegistroDTO dto) {
        if (usuarioRepositorio.findByCorreo(dto.getCorreo()) != null) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());

        // Aquí aplicas la encriptación
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));

        usuario.setVerificado(false);

        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        // Crear token
        String token = UUID.randomUUID().toString();
        TokenVerificacion tokenVerificacion = new TokenVerificacion(
                null,
                token,
                usuarioGuardado,
                LocalDateTime.now().plusHours(24)
        );
        tokenRepositorio.save(tokenVerificacion);

        // Enviar correo
        correoServicio.enviarCorreoVerificacion(usuarioGuardado.getCorreo(), token);

        return usuarioGuardado;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

}
