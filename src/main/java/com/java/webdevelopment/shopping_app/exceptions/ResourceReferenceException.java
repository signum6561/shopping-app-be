package com.java.webdevelopment.shopping_app.exceptions;

public abstract class ResourceReferenceException extends RuntimeException {
    public ResourceReferenceException(String message) {
        super(message);
    }

    public abstract String getResourceName();
}
