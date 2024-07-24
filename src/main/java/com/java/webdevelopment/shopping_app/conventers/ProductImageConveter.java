package com.java.webdevelopment.shopping_app.conventers;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.ProductImage;

public class ProductImageConveter extends AbstractConverter<Set<ProductImage>, Set<String>> {

    @Override
    protected Set<String> convert(Set<ProductImage> productImages) {
        return productImages.stream()
            .map(i -> i.getLink())
            .collect(Collectors.toSet());
    }
    
}
