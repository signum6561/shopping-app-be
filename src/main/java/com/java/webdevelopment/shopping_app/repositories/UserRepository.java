package com.java.webdevelopment.shopping_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.webdevelopment.shopping_app.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
