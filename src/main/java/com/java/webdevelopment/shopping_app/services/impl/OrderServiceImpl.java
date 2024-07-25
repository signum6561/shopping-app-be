package com.java.webdevelopment.shopping_app.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.ItemListConventer;
import com.java.webdevelopment.shopping_app.entities.Order;
import com.java.webdevelopment.shopping_app.entities.OrderItem;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.enums.OrderStatus;
import com.java.webdevelopment.shopping_app.exceptions.IllegalOrderStatusException;
import com.java.webdevelopment.shopping_app.exceptions.OrderNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.ProductNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.payload.requests.ItemRequest;
import com.java.webdevelopment.shopping_app.payload.requests.OrderRequest;
import com.java.webdevelopment.shopping_app.payload.requests.UserOrderRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.OrderResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.repositories.OrderRepository;
import com.java.webdevelopment.shopping_app.repositories.ProductRepository;
import com.java.webdevelopment.shopping_app.repositories.UserRepository;
import com.java.webdevelopment.shopping_app.sercurity.UserPrincipal;
import com.java.webdevelopment.shopping_app.services.OrderService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

import jakarta.annotation.PostConstruct;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
            ModelMapper modelMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<Order, OrderResponse> entityToDto = modelMapper.createTypeMap(Order.class, OrderResponse.class);
        Converter<OrderStatus, String> orderStatusString = s -> s.getSource().toString();
        entityToDto.addMappings(
                mapper -> mapper.using(orderStatusString).map(Order::getStatus, OrderResponse::setStatus));

        entityToDto.addMappings(
                mapper -> mapper.using(new ItemListConventer()).map(Order::getItems, OrderResponse::setItems));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getPaginateOrder(Integer page, Integer pageSize) {
        Page<Order> orders = orderRepository.findAll(PageUtil.request(page, pageSize));
        List<OrderResponse> orderResponses = orders.get()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(orders.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(orders.getNumberOfElements());
        pageResponse.setData(orderResponses);
        return pageResponse;
    }

    @Override
    public PageResponse<OrderResponse> getCurrentUserOrders(UserPrincipal userPrincipal, Integer page, Integer pageSize) {
        return getOrdersByUser(userPrincipal.getId(), page, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getOrdersByUser(String userId, Integer page, Integer pageSize) {

        boolean isUserExist = userRepository.existsById(userId);
        if(!isUserExist) {
            throw new UserNotFoundException();
        }

        Pageable pageable = PageUtil.request(page, pageSize);
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        List<OrderResponse> orderDTOs = orders.get()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(orders.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(orders.getNumberOfElements());
        pageResponse.setData(orderDTOs);
        return pageResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getCurrentUserOrder(String id, UserPrincipal userPrincipal) {
        String userId = userPrincipal.getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        Order order = user.getOrder(id);
        if(order == null) {
            throw new OrderNotFoundException(); 
        }
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    @Transactional
    public ApiResponse deleteCurrentUserOrder(String id, UserPrincipal userPrincipal) {
        String userId = userPrincipal.getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        Order order = user.getOrder(id);
        if(order == null) {
            throw new OrderNotFoundException(); 
        }

        orderRepository.delete(order);
        return new ApiResponse(
                true,
                Contants.ORDER_DELETED_SUCCESS,
                HttpStatus.OK);
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        String userId = request.getUserId();
        String statusCode = request.getStatus();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!OrderStatus.existByCode(statusCode)) {
            throw new IllegalOrderStatusException();
        }

        OrderStatus status = OrderStatus.convert(statusCode);

        Order order = Order.builder()
                .id(IdUtil.generate())
                .status(status)
                .user(user)
                .date(status == OrderStatus.Success ? new Date() : new Date(0))
                .build();

        for (ItemRequest itemRequest : request.getItems()) {
            String productId = itemRequest.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();

            order.addItem(orderItem);
        }

        Order instertedOrder = orderRepository.save(order);
        return modelMapper.map(instertedOrder, OrderResponse.class);
    }

    @Override
    @Transactional
    public OrderResponse createCurrentUserOrder(UserOrderRequest request, UserPrincipal userPrincipal) {
        String userId = userPrincipal.getId();
        boolean isUserExist = userRepository.existsById(userPrincipal.getId());
        if(!isUserExist) {
            throw new UserNotFoundException();
        }
        
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(request.getItems());
        orderRequest.setStatus(OrderStatus.OnHold.getCode());
        orderRequest.setUserId(userId);
        return createOrder(orderRequest);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(String id, OrderRequest request) {
        String userId = request.getUserId();
        String statusCode = request.getStatus();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!OrderStatus.existByCode(statusCode)) {
            throw new IllegalOrderStatusException();
        }

        OrderStatus status = OrderStatus.convert(statusCode);

        order.setStatus(status);
        order.setDate(status == OrderStatus.Success ? new Date() : new Date(0));
        order.setUser(user);
        for (ItemRequest itemRequest : request.getItems()) {
            String productId = itemRequest.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();

            order.addItem(orderItem);
        }

        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }

    @Override
    @Transactional
    public ApiResponse deleteOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);

        return new ApiResponse(
                true,
                Contants.ORDER_DELETED_SUCCESS,
                HttpStatus.OK);
    }

}
