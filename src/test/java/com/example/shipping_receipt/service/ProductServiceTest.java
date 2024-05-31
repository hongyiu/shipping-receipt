package com.example.shipping_receipt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.shipping_receipt.entity.Category;
import com.example.shipping_receipt.entity.Product;
import com.example.shipping_receipt.repository.CategoryRepository;
import com.example.shipping_receipt.repository.ProductRepository;
import com.example.shipping_receipt.service.ProductService.ProductNameNotFoundException;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class ProductServiceTest {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    ProductServiceTest(CategoryRepository categoryRepository, ProductRepository productRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @BeforeEach
    void setupDatabase() {
        final Category category = new Category("clothing");
        category.setTaxExemptLocations(Collections.emptySet());
        final Category savedCategory = categoryRepository.save(category);
        productRepository.save(new Product("shirt", savedCategory));
    }

    @Test
    @Transactional
    void givenProductName_whenFindByName_thenReturnCorrectProduct() {
        final String productName = "shirt";
        final String expectedCategoryId = "clothing";

        final Product product = productService.findByName(productName);

        assertEquals(productName, product.getName(), "Product name should match");
        assertEquals(expectedCategoryId, product.getCategory().getName(), "Product category name should match");

    }

    @Test
    void givenInvalidProductName_whenFindByName_thenThrowProductNameNotFoundException() {
        final String invalidProductName = "INVALID_NAME";

        final ProductNameNotFoundException exception = assertThrows(ProductNameNotFoundException.class, () -> {
            productService.findByName(invalidProductName);
        });

        assertEquals(invalidProductName, exception.getName(), "The exception's name should match the invalid product name");

    }

    @AfterEach
    void setupAfterTransaction() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

}
