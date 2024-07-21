package com.java.webdevelopment.shopping_app.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    private String id;

    @NotNull
    @Column(name = "product_name")
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<ProductImage> images;

    public Product() {
        images = new HashSet<>();
    }

    public static ProductBuilder builder() {
        return new ProductBuilder().images(new HashSet<>());
    }

    public void addImage(ProductImage image) {
        images.add(image);
    }

    public ProductImage removeImage(ProductImage image) {
        return removeImage(image.getId());
    }

    public ProductImage removeImage(String id) {
        return images.stream()
                .filter(i -> i.getId().equals(id))
                .peek(image -> images.remove(image))
                .findFirst()
                .orElse(null);
    }

    public boolean isInStock() {
        return amount > 0;
    }

    @Override
    public String toString() {
        String imageIds = images == null
                ? null
                : images.stream()
                        .map(i -> i.getId())
                        .toString();
        return "Product [id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", amount=" + amount +
                ", category=" + category != null ? category.getId() : null +
                ", description=" + description +
                ", images=" + imageIds +
                "]";
    }
}
