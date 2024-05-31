package com.example.shipping_receipt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.shipping_receipt.entity.Category;
import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.repository.LocationRepository;
import com.example.shipping_receipt.service.LocationService.LocationIdNotFoundException;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class LocationServiceTest {

    private final LocationRepository locationRepository;
    private final LocationService locationService;

    @Autowired
    LocationServiceTest(LocationRepository locationRepository, LocationService locationService) {
        this.locationRepository = locationRepository;
        this.locationService = locationService;
    }

    @BeforeEach
    void setupDatabase() {
        locationRepository.save(new Location("NY", "New York", 1.00d, Collections.emptySet()));
    }

    @Test
    @Transactional
    void givenLocationId_whenFindById_thenReturnCorrectLocation() {
        final String locationId = "NY";
        final String expectedName = "New York";
        final double expectedTaxRate = 1.00d;
        final Set<Category> expectedTaxExemptCategories = Collections.emptySet();

        final Location location = locationService.findById(locationId);

        assertEquals(locationId, location.getId(), "Location ID should match");
        assertEquals(expectedName, location.getName(), "Location name should match");
        assertEquals(expectedTaxRate, location.getTaxRate(), "Location tax rate should match");
        assertEquals(expectedTaxExemptCategories, location.getTaxExemptCategories(), "Location tax exempt categories should match");
    }

    @Test
    void givenInvalidLocationId_whenFindById_thenThrowLocationIdNotFoundException() {
        final String invalidLocationId = "INVALID_ID";

        final LocationIdNotFoundException exception = assertThrows(LocationIdNotFoundException.class, () -> {
            locationService.findById(invalidLocationId);
        });

        assertEquals(invalidLocationId, exception.getId(), "The exception's ID should match the invalid location ID");

    }

    @AfterEach
    void setupAfterTransaction() {
        locationRepository.deleteAll();
    }

}
