package com.java.webdevelopment.shopping_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.webdevelopment.shopping_app.entities.OrderItem;
import com.java.webdevelopment.shopping_app.entities.OrderItemKey;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {

}
