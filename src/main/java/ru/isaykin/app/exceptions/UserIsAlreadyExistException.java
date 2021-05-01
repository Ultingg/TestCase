package ru.isaykin.app.exceptions;

public class UserIsAlreadyExistException extends RuntimeException {
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
