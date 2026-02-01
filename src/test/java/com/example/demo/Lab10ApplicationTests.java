package com.example.demo;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class Lab10ApplicationTests {

    @Test
    void contextLoads() {
    }

    @ExtendWith(MockitoExtension.class)
    static
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

            when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");

            // 2. Metodu Çalıştır
            userService.register(request);

            // 3. Kontrol Et: Şifre encode edildi mi? Kayıt fonksiyonu çağrıldı mı?
            verify(passwordEncoder).encode("plainPassword"); // Şifreleme metodu çalıştı mı?
            verify(userRepository).save(any(User.class));    // Veritabanına kayıt gitti mi?
        }
    }
}
