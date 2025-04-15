package com.ms.notesapplication.service;

import com.ms.notesapplication.entity.Notes;
import com.ms.notesapplication.exception.NoteIdNotFoundException;
import com.ms.notesapplication.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImp implements NotesService {

    @Autowired
    NotesRepository notesRepository;

    @Override
    public List<Notes> findNotesForUser(String username) {
        List<Notes> notesListByUser = notesRepository.findByOwnerUsername(username);

        return notesListByUser;
    }

    @Override
    public Notes createNotesForUser(String username, String content) {
        Notes notes = new Notes();
        notes.setOwnerUsername(username);
        notes.setContent(content);
        Notes savedNote = notesRepository.save(notes);
        return savedNote;
    }

    @Override
    public Notes updateNotesForUser(Long noteId, String username, String content) {

        Notes note = notesRepository.findById(noteId)
                .orElseThrow(()-> new NoteIdNotFoundException("Note Id not found : "+ noteId));
        note.setContent(content);
        Notes updatedNote = notesRepository.save(note);

        return updatedNote;
    }

    @Override
    public void deleteNotesForUser(Long noteId, String username) {
        notesRepository.deleteById(noteId);
    }

    @Override
    public Notes findNoteById(Long noteId) {
        Optional<Notes> note = notesRepository.findById(noteId);
        return note.orElseThrow(()-> new NoteIdNotFoundException("Note Id not found : "+ noteId));
    }
}
