package com.java.webdevelopment.shopping_app.entities;

import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "permission")
@AllArgsConstructor 
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Permission {

    @Id
    private String id;

    @NotNull
    @Column(unique = true)
    private String code;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public Permission() {
        roles = new HashSet<>();
    }

    public static PermissionBuilder builder() {
        return new PermissionBuilder().roles(new HashSet<>());
    }

    @Override
    public String toString() {
        String roleIds = roles == null
                ? null
                : roles.stream()
                        .map(r -> r.getId())
                        .toString();
        return "Permission [id=" + id + ", code=" + code + ", roles=" + roleIds + "]";
    }
}
