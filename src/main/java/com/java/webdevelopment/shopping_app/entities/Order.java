package com.java.webdevelopment.shopping_app.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Order")
@Data
@AllArgsConstructor
@Builder
public class Order {
    
    @Id
	private String id;
    
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItem> items;

	@NotNull
	private OrderStatus status;

	@NotNull
	@Column(name = "billed_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Order() {
		items = new HashSet<>();
	}

	public Long getTotal() {
        return items.stream()
			.mapToLong(i -> i.getTotal())
			.sum();
    }
}
