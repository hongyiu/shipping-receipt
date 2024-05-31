package com.example.shipping_receipt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping_receipt.entity.Category;
import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.entity.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SpringBootTest
class TaxComponentTest {

    private final TaxComponent taxComponent;

    @Autowired
    public TaxComponentTest(TaxComponent taxComponent) {
        this.taxComponent = taxComponent;
    }

    @Test
    void givenNonTaxExemptCategory_whenCalculateTax_thenTaxIsCalculatedCorrectly() {

        Category category = new Category("clothing");
        Location location = new Location("NY", "New York", 1.00d, Collections.emptySet());
        category.setTaxExemptLocations(Collections.emptySet());
        Product product = new Product("shirt", category);
        BigDecimal price = new BigDecimal("100.59");
        
        BigDecimal expectedTax = new BigDecimal("1.01");

        BigDecimal actualTax = taxComponent.calculateTax(product, price, location);
                        
        Assertions.assertEquals(expectedTax, actualTax);
    }

    @Test
    void givenTaxExemptCategory_whenCalculateTax_thenTaxIsZero() {
        
        Category category = new Category("clothing");
        Location location = new Location("NY", "New York", 1.00d, Set.of(category));
        category.setTaxExemptLocations(Set.of(location));
        Product product = new Product("shirt", category);
        BigDecimal price = new BigDecimal("100");

        BigDecimal expectedTax = BigDecimal.ZERO;

        BigDecimal actualTax = taxComponent.calculateTax(product, price, location);

        Assertions.assertEquals(expectedTax, actualTax);
    }

}
