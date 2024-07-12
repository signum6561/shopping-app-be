package com.java.webdevelopment.shopping_app.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
    
	private Integer quantity;
}
