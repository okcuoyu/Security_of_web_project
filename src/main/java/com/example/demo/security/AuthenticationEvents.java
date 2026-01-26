package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);


    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // Kullanıcı adı nedir
        String username = event.getAuthentication().getName();
        // Hata sebebi nedir
        String error = event.getException().getMessage();


        logger.warn("Failed login attempt for user: {}. Reason: {}", username, error);
    }

    @EventListener
    public void onAuthorizationFailure(AuthorizationDeniedEvent event) {

        String username = event.getAuthentication().get().getName();

        if ("anonymousUser".equals(username)) {
            return;
        }

        logger.warn("Unauthorized access attempt! User '{}' tried to access a protected resource.", username);
    }
}