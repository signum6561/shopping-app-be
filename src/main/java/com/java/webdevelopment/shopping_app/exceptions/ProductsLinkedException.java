package com.java.webdevelopment.shopping_app.exceptions;

import com.java.webdevelopment.shopping_app.constants.Contants;

public class ProductsLinkedException extends ResourceReferenceException {

    public ProductsLinkedException() {
        super(Contants.CATEGORY_REF_PRODUCT);
    }

    @Override
    public String getResourceName() {
        return "product";
    }
    
}
