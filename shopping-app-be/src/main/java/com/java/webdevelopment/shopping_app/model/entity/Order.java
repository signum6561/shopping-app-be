package com.java.webdevelopment.shopping_app.model.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Entity(name = "Order")
@Table(name = "Order")
@Data
@AllArgsConstructor
@Builder
public class Order {

	//attribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private Set<OrderItem> oderItem;

	@Column(name ="status")
	private OrderStatus status;

	@Column(name = "billed_date")
	@Temporal(TemporalType.DATE)
	private Date date;
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, 
        foreignKey = @ForeignKey(name = "fk_order_user"))
	private User user;


	//constructor
	public Order() {
		oderItem = new HashSet<>();
	}

   //method
	public long getTotal() {
		long total = 0;
		for (OrderItem items : oderItem) {
			total += total + items.getTotal();
		}
		return total;
	}
}
