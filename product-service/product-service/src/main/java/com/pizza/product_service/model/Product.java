package com.pizza.product_service.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Product {
    @Id
    private String id;
    private String sku;
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private Boolean active;
}