package com.java.webdevelopment.shopping_app.conventers;

import java.util.Set;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.ProductImage;

public class DefaultImageConvert extends AbstractConverter<Set<ProductImage>, String> {

    @Override
    protected String convert(Set<ProductImage> productImages) {
        ProductImage defaultImage = productImages
                .stream()
                .findFirst()
                .orElse(null);
        if (defaultImage != null) {
            return defaultImage.getLink();
        }
        return null;
    }
}
