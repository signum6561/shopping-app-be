package com.java.webdevelopment.shopping_app.entities;

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
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "category")
@AllArgsConstructor 
@Data
@Builder
@EqualsAndHashCode(of = "id")
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

    public static CategoryBuilder builder() {
        return new CategoryBuilder().products(new HashSet<>());
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public String toString() {
        String productIds = products == null
                ? null
                : products.stream()
                        .map(p -> p.getId())
                        .toString();
        return "Category [id=" + id +
                ", name=" + name +
                ", products=" + productIds + "]";
    }

}
