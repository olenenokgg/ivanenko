package ru.university.ivanenko14.exception;

public class ContactNotFoundException extends BusinessException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}