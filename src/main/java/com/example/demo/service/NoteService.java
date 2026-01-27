package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }


    public List<Note> getNotesForUser(String username) {
        return noteRepository.findAllByUserUsername(username);
    }


    public void createNote(String username, String title, String content) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);

        noteRepository.save(note);
    }


    public Note getNoteById(Long id, String username) {
        return noteRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new RuntimeException("Note not found or access denied"));
    }

    public void deleteNote(Long noteId, String username) {
        Note note = getNoteById(noteId, username);
        noteRepository.delete(note);
    }

    public void updateNote(Long id, String username, String newTitle, String newContent) {
        Note note = getNoteById(id, username);
        note.setTitle(newTitle);
        note.setContent(newContent);
        noteRepository.save(note);
    }
}