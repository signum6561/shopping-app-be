package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class CategoryNotFoundException extends ResourcesNotFoundException {

    public CategoryNotFoundException() {
        super(Contants.DEFAULT_NOT_FOUND("category"));
    }

    public CategoryNotFoundException(String id) {
        super(Contants.CATEGORY_NOT_FOUND(id));
    }

    @Override
    public String getResourceName() {
        return "category";
    }
}
