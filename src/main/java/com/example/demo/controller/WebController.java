package com.example.demo.controller;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;


@Controller
public class WebController {

    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    // Login Sayfası
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Kayıt Formu
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "register";
    }

    // Kayıt İşlemi
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid CreateUserRequest req) {
        userService.register(req);
        return "redirect:/login";
    }

    // Ana Sayfa
    @GetMapping("/user/home")
    public String userHome(Model model, Principal principal, Authentication authentication) {
        // DİKKAT: Yukarıdaki parantezin içine 'Authentication authentication' ekledik!

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        // Admin mi kontrolü
        boolean isAdmin = false;
        if (authentication != null) {
            isAdmin = authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return "user/home";
    }
}