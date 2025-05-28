package com.redesnutricion.usuarios.servicio;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoServicio {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoVerificacion(String destino, String token) {
        String asunto = "Verifica tu cuenta";
        String mensaje = "Haz clic en el siguiente enlace para verificar tu cuenta:\n\n" +
                "http://localhost:8080/usuarios/validar?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destino);
        mail.setSubject(asunto);
        mail.setText(mensaje);

        mailSender.send(mail);
    }
}