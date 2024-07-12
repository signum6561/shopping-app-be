package com.java.webdevelopment.shopping_app.entity;

import java.util.Date;
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
    
	@OneToMany(
        cascade = CascadeType.ALL, 
        mappedBy = "user")
	private Set<OrderItem> items;

    @Column(nullable = false)
	private OrderStatus status;

	@Column(name = "billed_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
	private User user;
}
