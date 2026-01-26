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

    // 1. Controller bu metodu çağırıyor: Kullanıcının notlarını getir
    public List<Note> getNotesForUser(String username) {
        // Önce kullanıcıyı buluyoruz
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Sonra o kullanıcının ID'sine ait notları veritabanından çekiyoruz
        return noteRepository.findAllByUserId(user.getId());
    }

    // 2. Controller bu metodu çağırıyor: Yeni not ekle
    // DİKKAT: Burada NoteRequest yerine direkt String title, content alıyoruz
    public void createNote(String username, String title, String content) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user); // İlişkiyi kuruyoruz (Foreign Key)

        noteRepository.save(note);
    }
    // NoteService.java dosyasının içine, en alta ekle:

    public void deleteNote(Long noteId, String username) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Sadece notun sahibi silebilir!
        if (note.getUser().getUsername().equals(username)) {
            noteRepository.delete(note);
        } else {
            throw new RuntimeException("You are not allowed to delete this note!");
        }
    }
    // NoteService.java dosyasının içine ekle:

    // 1. Düzenlenecek notu getir (Sahibi o mu diye kontrol ederek)
    public Note getNoteById(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You cannot edit this note!");
        }
        return note;
    }

    // 2. Notu güncelle
    public void updateNote(Long id, String username, String newTitle, String newContent) {
        Note note = getNoteById(id, username); // Yukarıdaki metodu kullanıyoruz
        note.setTitle(newTitle);
        note.setContent(newContent);
        noteRepository.save(note);
    }
}