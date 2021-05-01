package ru.isaykin.app.exceptions;

public class IncorrectStatusException extends RuntimeException {

    public IncorrectStatusException(String message) {
        super(message);
    }
}
