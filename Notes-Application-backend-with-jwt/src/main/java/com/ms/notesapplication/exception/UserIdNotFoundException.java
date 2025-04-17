package com.ms.notesapplication.exception;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message){
        super(message);
    }
}
