package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class EmailAlreadyExistException extends AlreadyExistsException {
    public EmailAlreadyExistException(String email) {
        super(Contants.EMAIL_ALREADY_EXIST(email));
    }

    @Override
    public String getField() {
        return "email";
    }
}
