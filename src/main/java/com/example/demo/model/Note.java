package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank sildik (Kütüphane hatası riskine karşı)
    @Column(nullable = false)
    private String title;

    // @NotBlank sildik ve uzun yazılar için TEXT ayarı ekledik
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- CONSTRUCTORLAR ---

    // 1. Boş Constructor (JPA için şart)
    public Note() {
    }

    // 2. Dolu Constructor (Service için şart)
    public Note(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // --- GETTER & SETTER ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}