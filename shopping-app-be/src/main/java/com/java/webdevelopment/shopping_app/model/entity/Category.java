package com.java.webdevelopment.shopping_app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity(name="Category")
@Table
@AllArgsConstructor 
@Data
@Builder
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
	@Column (name="category_name",nullable=false)
	private final String name;
    //constructor
    
	
    
	
}
