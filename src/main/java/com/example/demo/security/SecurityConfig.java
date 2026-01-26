package com.example.demo.security;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter; // Referrer Policy için gerekli import

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginSuccessHandler successHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          CustomLoginSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. YETKİLENDİRME AYARLARI (Mevcut hali)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/error").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/notes/**", "/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                // 2. GİRİŞ İŞLEMLERİ (Mevcut hali)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                // 3. ÇIKIŞ VE SESSION YÖNETİMİ (Lab 1.1 - Mevcut hali gayet iyi)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)       // Session'ı sunucudan sil
                        .deleteCookies("JSESSIONID")       // Tarayıcıdaki çerezi sildir
                        .permitAll()
                )
                // 4. GÜVENLİK BAŞLIKLARI (Lab Task 2 - YENİ EKLENDİ)
                .headers(headers -> headers
                        // 2.1 X-Content-Type-Options: nosniff (Tarayıcının dosya türü tahminini engeller) [cite: 27, 29]
                        .contentTypeOptions(ops -> ops.disable()) // Spring Security default olarak açar ama explicit belirtmek iyidir.

                        // 2.2 X-Frame-Options: DENY (Clickjacking saldırısını engeller)
                        .frameOptions(frame -> frame.deny())

                        // 2.3 Content-Security-Policy (CSP): Sadece kendi sitemizden script/css yüklenmesine izin ver
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:;")
                        )

                        // 2.4 Referrer-Policy: Başka siteye giderken adres bilgisini gizle [cite: 38, 39]
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}