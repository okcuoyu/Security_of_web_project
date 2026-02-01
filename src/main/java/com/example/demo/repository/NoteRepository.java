package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {


    Optional<Note> findByIdAndUserUsername(Long id, String username);


    List<Note> findAllByUserUsername(String username);
}