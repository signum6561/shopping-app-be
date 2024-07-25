package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class RoleCodeAlreadyExistException extends AlreadyExistsException {

    public RoleCodeAlreadyExistException(String code) {
        super(Contants.ROLE_CODE_ALREADY_EXIST(code));
    }

    @Override
    public String getField() {
        return "roleCode";
    }
    
}
