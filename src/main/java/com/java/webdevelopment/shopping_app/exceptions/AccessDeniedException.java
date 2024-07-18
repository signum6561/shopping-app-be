package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super(Contants.ACCESS_DENIED);
    }
}
