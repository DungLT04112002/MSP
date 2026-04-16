package com.pizza.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.pizza.order_service.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/product/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);

    @PostMapping("/api/product/{id}/reserve")
    ProductResponse reserveProduct(@PathVariable("id") String id, @RequestParam("quantity") Integer quantity);
}
