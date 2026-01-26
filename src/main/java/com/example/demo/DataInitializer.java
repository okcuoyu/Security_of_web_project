package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.findByUsername("yusufenes").isEmpty()) {
            User admin = new User();
            admin.setUsername("yusufenes");
            admin.setEmail("yusufenes@admin.com");
            admin.setPassword(passwordEncoder.encode("350948ft"));
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);
            System.out.println("--- ADMIN KULLANICISI OLUŞTURULDU ---");
            System.out.println("User: yusufenes");
        }
    }
}