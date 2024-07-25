package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class OrderNotFoundException extends ResourcesNotFoundException {

    public OrderNotFoundException() {
        super(Contants.DEFAULT_NOT_FOUND("order"));
    }

    public OrderNotFoundException(String id) {
        super(Contants.ORDER_NOT_FOUND(id));
    }

    @Override
    public String getResourceName() {
        return "order";
    }
    
}
