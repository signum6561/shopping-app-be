package com.java.webdevelopment.shopping_app.conventers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

import com.java.webdevelopment.shopping_app.entities.OrderStatus;

@Converter(autoApply = true)
public class OrderStatusConventer implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus != null ? orderStatus.getCode() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(OrderStatus.values())
                .filter(ot -> ot.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
