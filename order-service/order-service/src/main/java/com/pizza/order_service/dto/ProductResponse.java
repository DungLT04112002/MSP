package com.pizza.order_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
    private String id;
    private String sku;
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private Boolean active;
}
