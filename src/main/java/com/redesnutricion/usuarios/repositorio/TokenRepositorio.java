package com.redesnutricion.usuarios.repositorio;

import com.redesnutricion.usuarios.modelo.TokenVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepositorio extends JpaRepository<TokenVerificacion, Long> {
    TokenVerificacion findByToken(String token);
}
