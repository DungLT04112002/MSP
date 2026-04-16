package com.pizza.order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.order_service.model.Product;
import com.pizza.order_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product productRequest) {
        productRepository.save(productRequest);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}