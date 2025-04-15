package com.ms.notesapplication.exception;

public class NoteIdNotFoundException extends RuntimeException {
    public NoteIdNotFoundException(String message) {
        super(message);
    }
}
