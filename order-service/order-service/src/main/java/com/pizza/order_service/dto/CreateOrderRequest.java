package com.pizza.order_service.dto;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String customerName;
    private String customerEmail;
    private String productId;
    private Integer quantity;
}
