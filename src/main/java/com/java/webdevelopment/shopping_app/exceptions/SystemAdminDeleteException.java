package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class SystemAdminDeleteException extends RuntimeException {

    public  SystemAdminDeleteException() {
        super(Contants.SYSTEM_ADMIN_DELETE);
    }
    
}
