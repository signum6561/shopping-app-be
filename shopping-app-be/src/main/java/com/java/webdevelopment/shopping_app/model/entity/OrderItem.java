package com.java.webdevelopment.shopping_app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Entity(name="Order_detail")
@Table
@Data
@AllArgsConstructor
@Builder
public class OrderItem {
    
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false, 
        foreignKey = @ForeignKey(name = "fk_Order_detail__order"))
	private Order order;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, 
        foreignKey = @ForeignKey(name = "fk_Order_detail__product"))
	private Product product;
    
	@Column(name="quantity")
	private Integer quantity;
	
	
	


	public long getTotal()
	{
		long total=0;
		total=product.getPrice()*quantity;
		return total;
	}
}
