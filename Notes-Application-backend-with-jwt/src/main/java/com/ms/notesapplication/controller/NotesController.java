package com.ms.notesapplication.controller;

import com.ms.notesapplication.dto.NoteRequest;
import com.ms.notesapplication.entity.Notes;
import com.ms.notesapplication.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/add")
    public ResponseEntity<Notes> addNotes(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody NoteRequest noteRequest) {
        String username = userDetails.getUsername();
        Notes savedNotes = notesService.createNotesForUser(username, noteRequest.getContent());

        System.out.println("UserDetails: " + username + " Notes: " + savedNotes);

        return new ResponseEntity<>(savedNotes, HttpStatus.CREATED);
    }

    @GetMapping("/getByUser")
    public ResponseEntity<List<Notes>> getNotesByUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        List<Notes> notesList = notesService.findNotesForUser(username);

        System.out.println("UserDetails: " + username + " Notes: " + notesList);

        return new ResponseEntity<>(notesList, HttpStatus.OK);
    }

    @PutMapping("/update/{noteId}")
    public ResponseEntity<Notes> updateNotes(@PathVariable("noteId") Long id,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody NoteRequest noteRequest) {
        String username = userDetails.getUsername();
        Notes updatedNotes = notesService.updateNotesForUser(id, username, noteRequest.getContent());

        System.out.println("UserDetails: " + username + " Notes: " + updatedNotes.toString());

        return new ResponseEntity<>(updatedNotes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<?> deleteNotes(@PathVariable("noteId") Long id,
                                         @AuthenticationPrincipal UserDetails userDetails) {

        try {
            Notes notes = notesService.findNoteById(id);
            if (notes != null) {
                String username = userDetails.getUsername();
                notesService.deleteNotesForUser(id, username);

                return new ResponseEntity<>("Deleted Notes Successfully", HttpStatus.OK);
            }

        } catch (RuntimeException e) {
            return new ResponseEntity<>("No Content found for the ID " + id, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("No Content found for the ID " + id, HttpStatus.NO_CONTENT);
    }
}
