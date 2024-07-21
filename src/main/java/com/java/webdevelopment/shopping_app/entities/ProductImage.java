package com.java.webdevelopment.shopping_app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_images")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class ProductImage {

    @Id
	private String id;
    
	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
     
    @NotNull
	private String link;

	@Override
	public String toString() {
		return "ProductImage [id=" + id + 
				", product=" + product != null ? product.getId() : null + 
				", link=" + link + 
				"]";
	}
}
