package com.example.shipping_receipt.controller;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import static org.hamcrest.Matchers.containsString;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class PrinterControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final MockMvc mockMvc;

    @Autowired
    PrinterControllerTest(CategoryRepository categoryRepository, ProductRepository productRepository, LocationRepository locationRepository, MockMvc mockMvc) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
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
    void testCreateReceipt() throws Exception {
        final String format = """
                    {
                        "location": "%s",
                        "products": [
                            {
                                "name": "%s",
                                "quantity": %d,
                                "price": %f
                            }
                        ]
                    }
                """;

        final String location = "NY";
        final String productName = "shirt";
        final int quantity = 1;
        final double price = 100.00;
        String request = String.format(format, location, productName, quantity, price);

        mockMvc.perform(get("/receipts/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());

        final String logOutput = outContent.toString() + errContent.toString();
        assertTrue(logOutput.contains(productName), "Log output should contain the product name");
        assertTrue(logOutput.contains(String.format("$%.2f", price)), "Log output should contain the product price");
        assertTrue(logOutput.contains("subtotal:"), "Log output should contain subtotal");
        assertTrue(logOutput.contains("tax:"), "Log output should contain tax");
        assertTrue(logOutput.contains("total:"), "Log output should contain total");
        
    }

    @Test
    @Transactional
    void testCreateReceiptWithInvalidLocation() throws Exception {
        final String format = """
                    {
                        "location": "%s",
                        "products": [
                            {
                                "name": "%s",
                                "quantity": %d,
                                "price": %f
                            }
                        ]
                    }
                """;

        final String location = "WRONG_LOCATION";
        final String productName = "shirt";
        final int quantity = 1;
        final double price = 100.00;
        String request = String.format(format, location, productName, quantity, price);

        mockMvc.perform(get("/receipts/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Location not found")))
                .andExpect(content().string(containsString(location)));

    }

    @Test
    @Transactional
    void testCreateReceiptWithInvalidProduct() throws Exception {
        final String format = """
                    {
                        "location": "%s",
                        "products": [
                            {
                                "name": "%s",
                                "quantity": %d,
                                "price": %f
                            }
                        ]
                    }
                """;

        final String location = "NY";
        final String productName = "WRONG_PRODUCT";
        final int quantity = 1;
        final double price = 100.00;
        String request = String.format(format, location, productName, quantity, price);

        mockMvc.perform(get("/receipts/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Product not found")))
                .andExpect(content().string(containsString(productName)));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @AfterEach
    void setupAfterTransaction() {
        productRepository.deleteAll();
        locationRepository.deleteAll();
        categoryRepository.deleteAll();
    }

}