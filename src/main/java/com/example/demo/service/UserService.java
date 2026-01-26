package com.example.demo.service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // KAYIT OLMA İŞLEMİ (WebController kullanıyor)
    public void register(CreateUserRequest req) {
        // Aynı kullanıcı adında biri var mı kontrolü (Opsiyonel ama iyi olur)
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        // Şifreyi şifreleyerek kaydet (BCrypt)
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    // YARDIMCI METOT (Gerekirse kullanıcıyı bulmak için)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Admin paneli için tüm kullanıcıları getir
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Kullanıcıyı ID ile sil
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}