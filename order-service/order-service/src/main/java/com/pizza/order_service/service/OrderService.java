package com.pizza.order_service.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pizza.order_service.client.ProductClient;
import com.pizza.order_service.dto.CreateOrderRequest;
import com.pizza.order_service.dto.ProductResponse;
import com.pizza.order_service.model.Order;
import com.pizza.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Order createOrder(CreateOrderRequest request) {
        validateRequest(request);

        ProductResponse product = productClient.getProductById(request.getProductId());

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is inactive");
        }

        if (product.getAvailableQuantity() == null || product.getAvailableQuantity() < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product does not have enough stock");
        }

        productClient.reserveProduct(request.getProductId(), request.getQuantity());

        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .productId(product.getId())
                .productSku(product.getSku())
                .productName(product.getName())
                .quantity(request.getQuantity())
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .status("CREATED")
                .createdAt(Instant.now())
                .build();

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private void validateRequest(CreateOrderRequest request) {
        if (request.getProductId() == null || request.getProductId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productId is required");
        }
        if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customerName is required");
        }
        if (request.getCustomerEmail() == null || request.getCustomerEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customerEmail is required");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be greater than 0");
        }
    }
}
