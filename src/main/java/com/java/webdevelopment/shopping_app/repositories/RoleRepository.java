package com.java.webdevelopment.shopping_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.webdevelopment.shopping_app.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    
    Optional<Role> findByCode(String code);

    boolean existsByCode(String code);
    
}
