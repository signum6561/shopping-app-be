package com.java.webdevelopment.shopping_app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.webdevelopment.shopping_app.entities.Order;
import com.java.webdevelopment.shopping_app.entities.User;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
