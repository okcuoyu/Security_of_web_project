package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // List Notes
    @GetMapping
    public String listNotes(Model model, Principal principal) {
        String username = principal.getName();
        List<Note> notes = noteService.getNotesForUser(username);
        model.addAttribute("notes", notes);
        return "user/notes";
    }

    // Create New Note
    @PostMapping
    public String createNote(@RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {
        noteService.createNote(principal.getName(), title, content);
        return "redirect:/notes";
    }

    // Delete Note
    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id, Principal principal) {
        // Validation: Check for negative ID
        if (id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must be positive");
        }

        try {
            noteService.deleteNote(id, principal.getName());
            return "redirect:/notes";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }

    // Show Edit Page
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {

        if (id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Note ID");
        }

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

    // Update Note
    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {

        if (id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must be positive");
        }

        try {
            noteService.updateNote(id, principal.getName(), title, content);
            return "redirect:/notes";
        } catch (Exception e) {
            return "redirect:/access-denied";
        }
    }
}