package com.java.webdevelopment.shopping_app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItemKey implements Serializable {
    
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private String productId;
}
