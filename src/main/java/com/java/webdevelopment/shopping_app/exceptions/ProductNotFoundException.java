package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class ProductNotFoundException extends ResourcesNotFoundException {
    public ProductNotFoundException() {
        super(Contants.PRODUCT_NOT_FOUND);
    }

    public ProductNotFoundException(String id) {
        super(Contants.PRODUCT_NOT_FOUND(id));
    }

    @Override
    public String getResource() {
        return "product";
    }
}
