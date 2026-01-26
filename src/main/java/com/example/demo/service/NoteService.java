package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    //Kullanıcının notlarını getir
    public List<Note> getNotesForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return noteRepository.findAllByUserId(user.getId());
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


    public void deleteNote(Long noteId, String username) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));


        if (note.getUser().getUsername().equals(username)) {
            noteRepository.delete(note);
        } else {
            throw new RuntimeException("You are not allowed to delete this note!");
        }
    }


    public Note getNoteById(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You cannot edit this note!");
        }
        return note;
    }


    public void updateNote(Long id, String username, String newTitle, String newContent) {
        Note note = getNoteById(id, username);
        note.setTitle(newTitle);
        note.setContent(newContent);
        noteRepository.save(note);
    }
}