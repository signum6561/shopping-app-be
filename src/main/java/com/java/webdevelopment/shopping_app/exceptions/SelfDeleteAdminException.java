package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class SelfDeleteAdminException extends RuntimeException {
    public  SelfDeleteAdminException() {
        super(Contants.ADMIN_SELF_DELETE);
    }
}
