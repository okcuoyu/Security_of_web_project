package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    // SLF4J ile loglama yapılması isteniyor
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    //  HATALI GİRİŞ DENEMELERİNİ YAKALAR
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // Kullanıcı adı nedir
        String username = event.getAuthentication().getName();
        // Hata sebebi nedir
        String error = event.getException().getMessage();

        //  Sadece kullanıcı adı ve hatayı logluyoruz ama şıfreyı loglamıyoruz.
        logger.warn("Failed login attempt for user: {}. Reason: {}", username, error);
    }

    // Normal 'User' admin paneline girmeye çalışırsa bu çalışır
    @EventListener
    public void onAuthorizationFailure(AuthorizationDeniedEvent event) {
        // Kimin girmeye çalıştığını goruruz
        String username = event.getAuthentication().get().getName();

        logger.warn("Unauthorized access attempt! User '{}' tried to access a protected resource.", username);
    }
}