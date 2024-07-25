package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    
    private String id;
    private String userId;
    private String status;
    private Date billedDate;
    private Long total;
    private Set<ItemResponse> items;

    public OrderResponse() {
        items = new HashSet<>();
    }
}
