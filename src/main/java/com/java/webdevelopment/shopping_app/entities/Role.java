package com.java.webdevelopment.shopping_app.entities;

import java.util.HashSet;
import java.util.Set;

import com.java.webdevelopment.shopping_app.utils.Extensions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

@Entity
@Table(name = "role")
@Data
@Builder
@AllArgsConstructor
@ExtensionMethod(Extensions.class) 
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
	private String id;

	@NotNull
	@Column(name = "role_name")
	private String name;

	@NotNull
	private boolean admin;
    
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public Role() {
		users = new HashSet<>();
	}

	public static RoleBuilder builder() {
		return new RoleBuilder().users(new HashSet<>());
	}

	public User getUserById(String id) {
		return users.stream()
			.filter(u -> u.getId().equals(id))
			.findFirst()
			.orElse(null);
	}

	public User getUser(String username) {
		return users.stream()
			.filter(u -> u.getUsername().equals(username))
			.findFirst()
			.orElse(null);
	}

	public void addUser(User user) {
		users.add(user);
	}

	public boolean hasUser(User user) {
		return !user.isNullOrEmpty();
	}

	@Override
	public String toString() {
		String userIds = users == null
				? null
				: users.stream()
						.map(u -> u.getId())
						.toString();
		return "Role [id=" + id +
				", name=" + name +
				", admin=" + admin +
				", users=" + userIds +
				"]";
	}
	
}
