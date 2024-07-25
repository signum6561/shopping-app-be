package com.java.webdevelopment.shopping_app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.webdevelopment.shopping_app.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByUsername(String username);

    Page<User> findByRolesCode(String roleCode, Pageable pageable);

    boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
