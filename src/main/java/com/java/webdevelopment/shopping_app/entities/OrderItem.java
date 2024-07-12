package com.java.webdevelopment.shopping_app.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
@AllArgsConstructor
@Builder
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
    
    @NotNull
	private Integer quantity;

    public Long getTotal() {
        return product.getPrice() * Long.valueOf(quantity);
    }
}
