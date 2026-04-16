package com.pizza.product_service.controller;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestParam;

import com.pizza.product_service.model.Product;
import com.pizza.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Tạo mới sản phẩm
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        System.out.println("DEBUG: Dang dung Database: " + mongoTemplate.getDb().getName());
        if (product.getAvailableQuantity() == null) {
            product.setAvailableQuantity(0);
        }
        if (product.getActive() == null) {
            product.setActive(Boolean.TRUE);
        }
        return productRepository.save(product);
    }

    // 3. Lấy chi tiết một sản phẩm theo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // 4. Xóa sản phẩm
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }

    // 5. Cập nhật sản phẩm
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        existingProduct.setSku(productDetails.getSku());
        existingProduct.setName(productDetails.getName());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setAvailableQuantity(productDetails.getAvailableQuantity());
        existingProduct.setActive(productDetails.getActive());

        return productRepository.save(existingProduct);
    }

    @PostMapping("/{id}/reserve")
    @ResponseStatus(HttpStatus.OK)
    public Product reserveProduct(@PathVariable String id, @RequestParam Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is inactive");
        }

        if (product.getAvailableQuantity() == null || product.getAvailableQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
        return productRepository.save(product);
    }
}