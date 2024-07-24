package com.java.webdevelopment.shopping_app.entities;

import java.util.HashSet;
import java.util.Set;

import com.java.webdevelopment.shopping_app.utils.Extensions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	private String name;

	@NotNull
	private String code;
    
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "role_permission", 
        joinColumns = @JoinColumn(name = "role_id"), 
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions;

	public Role() {
		users = new HashSet<>();
		permissions = new HashSet<>();
	}

	public static RoleBuilder builder() {
		return new RoleBuilder()
			.users(new HashSet<>())
			.permissions(new HashSet<>());
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

	public boolean hasUsers() {
		return !users.isNullOrEmpty();
	}

	public boolean isAdminRole() {
		return code.equals("ADMIN");
	}

	public boolean isBaseRole() {
		return code.equals("ADMIN") || code.equals("USER");
	}

	public void addPermission(Permission permission) {
		permissions.add(permission);
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
				", code=" + code +
				", users=" + userIds +
				"]";
	}
	
}
