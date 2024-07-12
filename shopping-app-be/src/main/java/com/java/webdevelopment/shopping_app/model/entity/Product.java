package com.java.webdevelopment.shopping_app.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Product")
@Table
@Data
@AllArgsConstructor
@Builder
public  class Product {

	@Id
	private String id;

	@Column(name="product_name")
	private String name;

	@Column(name="price")
	private Integer price;
	
	@Column(name="amount")
	private Integer amount;
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false, 
        foreignKey = @ForeignKey(name = "fk_product_category"))
	private Category category;

	@Column(name="description")
	private String description;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Product")
	private Set<ProductImage> images;



	public Product() {
		images = new HashSet<>();
	}


	public void addImage(ProductImage image)
	{
		images.add(image);
	}
	
	public ProductImage removeProductImage(ProductImage image)
	{
		images.remove(image);
		return image;
	}
	// sua lai so do
	public Set<ProductImage> removeProductImage(String id)
	{
		for( ProductImage p : this.images)
		{
			if(p.getId().equals(id))
			images.remove(p);
		}
		return images;
	}
	
	public boolean isInStock()
	{
		return amount > 0;
	}
}
