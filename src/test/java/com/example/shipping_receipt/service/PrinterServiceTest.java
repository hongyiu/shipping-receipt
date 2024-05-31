package com.example.shipping_receipt.service;

import com.example.shipping_receipt.dto.CartProduct;
import com.example.shipping_receipt.dto.ReceiptItem;
import com.example.shipping_receipt.dto.ReceiptRequest;
import com.example.shipping_receipt.dto.ReceiptResponse;
import com.example.shipping_receipt.entity.Category;
import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.entity.Product;
import com.example.shipping_receipt.repository.CategoryRepository;
import com.example.shipping_receipt.repository.LocationRepository;
import com.example.shipping_receipt.repository.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class PrinterServiceTest {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final PrinterService printerService;

    @Autowired
    PrinterServiceTest(CategoryRepository categoryRepository, ProductRepository productRepository,
            LocationRepository locationRepository, PrinterService printerService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.printerService = printerService;
    }

    @BeforeEach
    void setupDatabase() {
        final Category category = new Category("clothing");
        category.setTaxExemptLocations(Collections.emptySet());
        final Category savedCategory = categoryRepository.save(category);
        productRepository.save(new Product("shirt", savedCategory));
        locationRepository.save(new Location("NY", "New York", 1.00d, Collections.emptySet()));
    }

    @Test
    @Transactional
    void testCreateReceipt() {
        final CartProduct cartProduct = new CartProduct("shirt", 1, 100.00);
        final ReceiptRequest receiptRequest = new ReceiptRequest(Set.of(cartProduct), "NY");
        final ReceiptItem receiptItem = new ReceiptItem("shirt", 1, new BigDecimal("100.00"), new BigDecimal("1.00"));
        final ReceiptResponse expectedResponse = new ReceiptResponse(Set.of(receiptItem), new BigDecimal("100.00"), new BigDecimal("1.00"), new BigDecimal("101.00"));

        final ReceiptResponse actualResponse = printerService.createReceipt(receiptRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @AfterEach
    void setupAfterTransaction() {
        productRepository.deleteAll();
        locationRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    
}