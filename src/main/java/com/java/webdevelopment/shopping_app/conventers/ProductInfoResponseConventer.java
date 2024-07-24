package com.java.webdevelopment.shopping_app.conventers;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;

public class ProductInfoResponseConventer extends AbstractConverter<Product, ProductInfoResponse> {

    @Override
    protected ProductInfoResponse convert(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

}
