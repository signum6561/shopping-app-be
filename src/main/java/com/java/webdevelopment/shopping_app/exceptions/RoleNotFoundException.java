package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class RoleNotFoundException extends ResourcesNotFoundException {

    public RoleNotFoundException() {
        super(Contants.DEFAULT_NOT_FOUND("role"));
    }

    public RoleNotFoundException(String code) {
        super(Contants.ROLE_NOT_FOUND(code));
    }

    @Override
    public String getResourceName() {
        return "role";
    }

}
