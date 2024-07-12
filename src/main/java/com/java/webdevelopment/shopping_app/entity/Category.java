package com.java.webdevelopment.shopping_app.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "category")
@AllArgsConstructor 
@Data
@Builder
public class Category {

    @Id
    private String id;
    
    @NotNull
	@Column(name="category_name")
	private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public Category() {
        products = new HashSet<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
