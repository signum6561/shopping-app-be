package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.OrderDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;

public interface OrderService {

    PageResponse<OrderDTO> getPaginateOrder(Integer page, Integer pageSize);

    PageResponse<OrderDTO> getCurrentUserOrders(UserPrincipal userPrincipal, Integer page, Integer pageSize);
    
    PageResponse<OrderDTO> getOrdersByUser(String userId, Integer page, Integer pageSize);

    OrderDTO getOrder(String id);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(String id, OrderDTO orderDTO);

    ApiResponse deleteOrder(String id);
}
