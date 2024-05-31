package com.example.shipping_receipt.service;

import org.springframework.stereotype.Service;

import com.example.shipping_receipt.entity.Product;
import com.example.shipping_receipt.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product findByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new ProductNameNotFoundException(name));
    }

    public class ProductNameNotFoundException extends RuntimeException {
        private final String name;

        protected ProductNameNotFoundException(String name) {
            super("Product not found with name: " + name);
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
