package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import jakarta.validation.constraints.Min; // EKLENDİ: En az kaç olacağını belirler
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated; // EKLENDİ: Kontrolü açar
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
@Validated
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Notları Listeleme
    @GetMapping
    public String listNotes(Model model, Principal principal) {
        String username = principal.getName();
        List<Note> notes = noteService.getNotesForUser(username);
        model.addAttribute("notes", notes);
        return "user/notes";
    }

    // Yeni Not Ekleme
    @PostMapping
    public String createNote(@RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {
        noteService.createNote(principal.getName(), title, content);
        return "redirect:/notes";
    }

    // Silme İşlemi
    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable @Min(value = 1, message = "ID negatif olamaz!") Long id,
                             Principal principal) {
        try {
            noteService.deleteNote(id, principal.getName());
            return "redirect:/notes";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }

    // Düzenleme Sayfası
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable @Min(1) Long id,
                               Model model,
                               Principal principal) {
        try {
            Note note = noteService.getNoteById(id, principal.getName());

            if (note == null) {
                return "redirect:/access-denied";
            }

            model.addAttribute("note", note);
            return "user/edit_note";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }

    // Güncelleme İşlemi ️
    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable @Min(1) Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {
        try {
            noteService.updateNote(id, principal.getName(), title, content);
            return "redirect:/notes";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }
}