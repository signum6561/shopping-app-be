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

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.Order;
import com.java.webdevelopment.shopping_app.entities.OrderItem;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.entities.User;
import com.java.webdevelopment.shopping_app.enums.OrderStatus;
import com.java.webdevelopment.shopping_app.exceptions.IllegalOrderStatusException;
import com.java.webdevelopment.shopping_app.exceptions.OrderNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.ProductNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.UserNotFoundException;
import com.java.webdevelopment.shopping_app.payload.OrderDTO;
import com.java.webdevelopment.shopping_app.payload.OrderItemDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
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
        TypeMap<Order, OrderDTO> entityToDto = modelMapper.createTypeMap(Order.class, OrderDTO.class);
        Converter<OrderStatus, String> orderStatusString = s -> s.getSource().getCode();
        entityToDto.addMappings(
                mapper -> mapper.using(orderStatusString).map(Order::getStatus, OrderDTO::setStatus));
    }

    @Override
    public PageResponse<OrderDTO> getPaginateOrder(Integer page, Integer pageSize) {
        Page<Order> orders = orderRepository.findAll(PageUtil.request(page, pageSize));
        List<OrderDTO> orderDTOs = orders.get()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();

        PageResponse<OrderDTO> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(orders.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(orders.getNumberOfElements());
        pageResponse.setData(orderDTOs);
        return pageResponse;
    }

    @Override
    public PageResponse<OrderDTO> getCurrentUserOrders(UserPrincipal userPrincipal, Integer page, Integer pageSize) {
        return getOrdersByUser(userPrincipal.getId(), page, pageSize);
    }

    @Override
    public PageResponse<OrderDTO> getOrdersByUser(String userId, Integer page, Integer pageSize) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Pageable pageable = PageUtil.request(page, pageSize);
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        List<OrderDTO> orderDTOs = orders.get()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();

        PageResponse<OrderDTO> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(orders.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(orders.getNumberOfElements());
        pageResponse.setData(orderDTOs);
        return pageResponse;
    }

    @Override
    public OrderDTO getOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String userId = orderDTO.getUserId();
        String statusCode = orderDTO.getStatus();

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

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            String productId = orderItemDTO.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(orderItemDTO.getQuantity())
                    .build();

            order.addItem(orderItem);
        }

        Order instertedOrder = orderRepository.save(order);
        return modelMapper.map(instertedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(String id, OrderDTO orderDTO) {
        String userId = orderDTO.getUserId();
        String statusCode = orderDTO.getStatus();

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
        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            String productId = orderItemDTO.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(orderItemDTO.getQuantity())
                    .build();

            order.addItem(orderItem);
        }

        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
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
