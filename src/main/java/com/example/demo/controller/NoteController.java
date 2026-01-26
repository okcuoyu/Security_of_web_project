package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes") // DÜZELTME 1: Adresi /notes yaptık (Home butonuyla eşleşti)
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Notları Listeleme
    @GetMapping
    public String listNotes(Model model, Principal principal) {
        // Giriş yapan kullanıcıyı bul
        String username = principal.getName();

        // Servisten o kullanıcının notlarını çek
        List<Note> notes = noteService.getNotesForUser(username);

        model.addAttribute("notes", notes);

        return "user/notes"; //
    }

    // Yeni Not Ekleme
    @PostMapping
    public String createNote(@RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {

        // DÜZELTME 3: DTO yerine basit parametreler kullandık (Service ile eşleşti)
        noteService.createNote(principal.getName(), title, content);

        return "redirect:/notes";
    }
    // NoteController.java dosyasının içine ekle:

    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id, Principal principal) {
        // Servisteki silme metodunu çağır
        noteService.deleteNote(id, principal.getName());

        // Sayfayı yenile
        return "redirect:/notes";
    }
    // NoteController.java dosyasının içine ekle:

    // 1. Düzenleme Sayfasını Göster (GET)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
        // Notu bul ve sayfaya gönder
        Note note = noteService.getNoteById(id, principal.getName());
        model.addAttribute("note", note);

        return "user/edit_note"; // Yeni oluşturacağımız html dosyası
    }

    // 2. Güncellemeyi Kaydet (POST)
    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {

        noteService.updateNote(id, principal.getName(), title, content);

        return "redirect:/notes"; // Listeye geri dön
    }

}