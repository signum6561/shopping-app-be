package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class IllegalOrderStatusException extends InvalidRequestException {
    public IllegalOrderStatusException() {
        super(Contants.INVALID_ORDER_STATUS);
    }
}
