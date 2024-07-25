package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.interfaces.HasPermission;
import com.java.webdevelopment.shopping_app.payload.requests.OrderRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.OrderResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.services.OrderService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/order")
@SecurityRequirement(name = "shopping-api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<OrderResponse>> getAllOrder(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage
    ) {
        PageResponse<OrderResponse> response = orderService.getPaginateOrder(perPage, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    @HasPermission("read_all:order")
    public ResponseEntity<OrderResponse> getOrder(
        @PathVariable String orderId
    ) {
        OrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @HasPermission("create:order")
    public ResponseEntity<OrderResponse> createOrder(
        @Valid @RequestBody OrderRequest newOrder
    ) {
        OrderResponse response = orderService.createOrder(newOrder);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}")
    @HasPermission("update:order")
    public ResponseEntity<OrderResponse> updateOrder(
        @Valid @RequestBody OrderRequest updateOrder,
        @PathVariable String orderId
    ) {
        OrderResponse response = orderService.updateOrder(orderId, updateOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    @HasPermission("delete:order")
    public ResponseEntity<ApiResponse> deleteOrder(
        @PathVariable String orderId
    ) {
        ApiResponse response = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }
}
