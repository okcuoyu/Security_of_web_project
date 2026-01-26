package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {


    Optional<Note> findByIdAndUserId(Long id, Long userId);


    @Query("SELECT n FROM Note n WHERE n.user.id = :userId")
    List<Note> findAllByUserId(@Param("userId") Long userId);
}
