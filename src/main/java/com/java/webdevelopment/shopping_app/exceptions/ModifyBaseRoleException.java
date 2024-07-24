package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class ModifyBaseRoleException extends RuntimeException {

    public ModifyBaseRoleException() {
        super(Contants.DENIED_BASE_ROLE_MODIFY);
    }

}
