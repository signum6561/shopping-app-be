package com.java.webdevelopment.shopping_app.model.entity;

import java.util.HashSet;
import java.util.Set;

import com.java.webdevelopment.shopping_app.utils.Extensions;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.ExtensionMethod;

@Entity(name = "Role")
@Table
@Data
@AllArgsConstructor
@Builder
@ExtensionMethod(Extensions.class) 
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(nullable = false)
	private  String name;

	@Column(nullable = false)
	private boolean admin;
    
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private  Set<User> users ;
	// constructor
   public Role()
   {
	users= new HashSet<>();
   } 



   public User getUserById(String id) {
    return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
}


	// xem lai
	public User getUser(User user) {
		return users.stream()
					.filter(u -> u.getId().equals(user.getId()))
					.findFirst()
					.orElse(null);
	}
	

	public void addUser(User user) {
		try {
            this.users.add(user);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public boolean hasUser(User user) {
		
		return !user.isNullOrEmpty();

	}
	 
	

}
