package com.java.webdevelopment.shopping_app.entities;

import java.util.HashSet;
import java.util.Set;

import com.java.webdevelopment.shopping_app.utils.Extensions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ExtensionMethod(Extensions.class)
public class User {

    @Id
    private String id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @OneToMany(
        cascade = CascadeType.ALL, 
        mappedBy = "user")
    private Set<Order> orders;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "role_detail", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        orders = new HashSet<>();
        roles = new HashSet<>();
    }

    public static UserBuilder builder() {
        return new UserBuilder()
            .orders(new HashSet<>())
            .roles(new HashSet<>());
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean isAdmin() {
        return roles.stream()
                .anyMatch(role -> role.isAdminRole());
    }

    public boolean hasRoles() {
        return !roles.isNullOrEmpty();
    }

    public void clearRoles() {
		roles.clear();
	}

    public Order getOrder(String id) {
        return orders.stream()
            .filter(o -> o.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String toString() {
        String orderIds = orders == null
                ? null
                : orders.stream()
                        .map(o -> o.getId())
                        .toString();
        String roleIds = roles == null
                ? null
                : roles.stream()
                        .map(r -> r.getId())
                        .toString();
        return "User [id=" + id +
                ", username=" + username +
                ", email=" + email +
                ", password=" + password +
                ", orders=" + orderIds +
                ", roles=" + roleIds +
                "]";
    }

}
