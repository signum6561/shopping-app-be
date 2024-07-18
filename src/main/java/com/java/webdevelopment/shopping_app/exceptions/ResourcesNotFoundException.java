package com.java.webdevelopment.shopping_app.exceptions;

public abstract class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String message) {
        super(message);
    }

    public abstract String getResource();
}
