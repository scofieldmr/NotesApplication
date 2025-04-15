package com.ms.notesapplication.service;

import com.ms.notesapplication.entity.Notes;

import java.util.List;

public interface NotesService {

    List<Notes> findNotesForUser(String username);

    Notes createNotesForUser(String username, String content);

    Notes updateNotesForUser(Long noteId, String username, String content);

    void deleteNotesForUser(Long noteId, String username);

    Notes findNoteById(Long noteId);
}
