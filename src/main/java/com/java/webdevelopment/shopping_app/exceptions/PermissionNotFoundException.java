package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class PermissionNotFoundException extends ResourcesNotFoundException {
    public PermissionNotFoundException() {
        super(Contants.DEFAULT_NOT_FOUND("permission"));
    }

    public PermissionNotFoundException(String code) {
        super(Contants.PERMISSION_NOT_FOUND(code));
    }

    @Override
    public String getResourceName() {
        return "permission";
    }
}
