package com.example.demo.service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldEncodePasswordWhenCreatingUser() {
        // 1. Hazırlık (Fake veri)
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("plainPassword");
        request.setEmail("test@test.com");

        // encode çağrıldığında "hashedPassword" dönmesini sağla
        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");

        // 2. Metodu Çalıştır (Senin kodunda adı 'register' idi, düzelttik!)
        userService.register(request);

        // 3. Kontrol Et: Şifre encode edildi mi? Kayıt fonksiyonu çağrıldı mı?
        verify(passwordEncoder).encode("plainPassword"); // Şifreleme metodu çalıştı mı?
        verify(userRepository).save(any(User.class));    // Veritabanına kayıt gitti mi?
    }
}