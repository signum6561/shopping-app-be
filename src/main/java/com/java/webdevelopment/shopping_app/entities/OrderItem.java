package com.java.webdevelopment.shopping_app.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "order_details")
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
    
    @NotNull
	private Integer quantity;

    public Long getTotal() {
        return product.getPrice() * Long.valueOf(quantity);
    }

    @Override
    public String toString() {
        return "OrderItem [id=" + id +
                ", order=" + order != null ? order.getId() : null +
                ", product=" + product != null ? product.getId() : null +
                ", quantity=" + quantity +
                "]";
    }
}
