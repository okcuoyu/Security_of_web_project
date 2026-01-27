package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // 1. GÜVENLİ ARAMA (User Isolation)
    // Sadece not ID'si yetmez, o notun sahibinin de (User'ın) username'i tutmalı.
    // Bu sayede User B, User A'nın notunu asla çekemez.
    Optional<Note> findByIdAndUserUsername(Long id, String username);


    List<Note> findAllByUserUsername(String username);
}