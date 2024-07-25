package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.requests.OrderRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserOrderRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.OrderResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface OrderService {

    PageResponse<OrderResponse> getPaginateOrder(Integer page, Integer pageSize);

    PageResponse<OrderResponse> getCurrentUserOrders(UserPrincipal userPrincipal, Integer page, Integer pageSize);
    
    PageResponse<OrderResponse> getOrdersByUser(String userId, Integer page, Integer pageSize);

    OrderResponse getCurrentUserOrder(String id, UserPrincipal userPrincipal);

    ApiResponse deleteCurrentUserOrder(String id, UserPrincipal userPrincipal);

    OrderResponse getOrder(String id);

    OrderResponse createOrder(OrderRequest request);

    OrderResponse createCurrentUserOrder(UserOrderRequest request, UserPrincipal userPrincipal);

    OrderResponse updateOrder(String id, OrderRequest request);

    ApiResponse deleteOrder(String id);
}
