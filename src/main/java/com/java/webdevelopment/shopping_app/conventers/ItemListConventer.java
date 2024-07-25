package com.java.webdevelopment.shopping_app.conventers;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.AbstractConverter;

import com.java.webdevelopment.shopping_app.entities.OrderItem;
import com.java.webdevelopment.shopping_app.payload.responses.ItemResponse;

public class ItemListConventer extends AbstractConverter<Set<OrderItem>, Set<ItemResponse>> {

    @Override
    protected Set<ItemResponse> convert(Set<OrderItem> orderItems) {
        Set<ItemResponse> res = new HashSet<>();
        for (OrderItem item : orderItems) {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setProductId(item.getId().getProductId());
            itemResponse.setProductName(item.getProduct().getName());
            itemResponse.setQuantity(item.getQuantity());  
            itemResponse.setItemTotal(item.getTotal());
            res.add(itemResponse);
        }
        return res;
    }
    
}
