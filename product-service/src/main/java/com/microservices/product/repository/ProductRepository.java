package com.microservices.product.repository;

import com.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional query methods can be defined here if needed
}
