package com.pizza.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pizza.product_service.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Spring sẽ tự động tạo các hàm save(), findAll(), findById() cho bạn
}
