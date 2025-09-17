package ru.balancewatcher.exception;

public class IllegalRequestException extends RuntimeException {
    public IllegalRequestException(String message) {
        super(message);
    }
}
