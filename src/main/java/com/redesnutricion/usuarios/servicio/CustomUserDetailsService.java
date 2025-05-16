package com.redesnutricion.usuarios.servicio;

import com.redesnutricion.usuarios.modelo.Usuario;
import com.redesnutricion.usuarios.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // ⚠️ Validamos si está verificado
        if (!usuario.isVerificado()) {
            throw new RuntimeException("Usuario no verificado. Revisa tu correo para validar la cuenta.");
        }

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena())
                .roles("USER")
                .build();
    }
}
