package com.java.webdevelopment.shopping_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.webdevelopment.shopping_app.entities.OrderItem;
import com.java.webdevelopment.shopping_app.entities.OrderItemKey;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {

}
