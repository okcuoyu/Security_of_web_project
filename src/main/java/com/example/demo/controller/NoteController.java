package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
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


    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id, Principal principal) {
        try {
            noteService.deleteNote(id, principal.getName());
            return "redirect:/notes";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
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


    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id,
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