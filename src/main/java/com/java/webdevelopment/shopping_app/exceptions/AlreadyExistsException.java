package com.java.webdevelopment.shopping_app.exceptions;

public abstract class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }

    public abstract String getField();
}
