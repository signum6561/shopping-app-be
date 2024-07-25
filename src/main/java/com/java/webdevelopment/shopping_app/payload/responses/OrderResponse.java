package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
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
