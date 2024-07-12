package com.java.webdevelopment.shopping_app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ProductImage")
@Table
@Data
@AllArgsConstructor
@Builder
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private String id;
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false, 
        foreignKey = @ForeignKey(name = "fk_productImage_product"))
	private Product product;
     
	@Column
	private String link;
	
}
