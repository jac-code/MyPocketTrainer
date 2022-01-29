package com.example.demo.exceptions;

public class InvalidVerificationTokenException extends Exception {
    public InvalidVerificationTokenException() {
        super();
    }

    public InvalidVerificationTokenException(String message) {
        super(message);
    }

    public InvalidVerificationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}