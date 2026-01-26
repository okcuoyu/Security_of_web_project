package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    // LAB 12: Ownership enforced via user_id
    Optional<Note> findByIdAndUserId(Long id, Long userId);

    // LAB 12: Custom parameterized query (prepared statement equivalent)
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId")
    List<Note> findAllByUserId(@Param("userId") Long userId);
}
