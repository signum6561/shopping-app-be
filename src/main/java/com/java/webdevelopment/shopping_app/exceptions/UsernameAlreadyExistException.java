package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class UsernameAlreadyExistException extends AlreadyExistsException {
    public UsernameAlreadyExistException(String username) {
        super(Contants.USERNAME_ALREADY_EXIST(username));
    }

    @Override
    public String getField() {
        return "username";
    }
}
