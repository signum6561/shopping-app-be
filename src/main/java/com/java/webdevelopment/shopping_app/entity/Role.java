package com.java.webdevelopment.shopping_app.entity;

import java.util.Set;

import com.java.webdevelopment.shopping_app.utils.Extensions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

@Entity
@Table(name = "role")
@Data
@Builder
@ExtensionMethod(Extensions.class) 
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
	private String id;

	@Column(nullable = false)
	private  String name;

	@Column(nullable = false)
	private boolean admin;
    
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

}
