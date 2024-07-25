package com.java.webdevelopment.shopping_app.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"orderId", "productId"})
@Embeddable
public class OrderItemKey implements Serializable {
    
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private String productId;
}
