package com.example.shipping_receipt.config;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.shipping_receipt.entity.Category;
import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.entity.Product;
import com.example.shipping_receipt.repository.CategoryRepository;
import com.example.shipping_receipt.repository.LocationRepository;
import com.example.shipping_receipt.repository.ProductRepository;

@Configuration
@Profile("dev")
public class SampleDataConfig implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    
    protected SampleDataConfig(CategoryRepository categoryRepository, ProductRepository productRepository, LocationRepository locationRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        final Category food = categoryRepository.save(new Category("food"));
        final Category clothing = categoryRepository.save(new Category("clothing"));
        final Category stationery = categoryRepository.save(new Category("stationery"));

        productRepository.save(new Product("apple", food));
        productRepository.save(new Product("banana", food));
        productRepository.save(new Product("potato chips", food));
        productRepository.save(new Product("shirt", clothing));
        productRepository.save(new Product("jeans", clothing));
        productRepository.save(new Product("socks", clothing));
        productRepository.save(new Product("pencil", stationery));
        productRepository.save(new Product("book", stationery));
        productRepository.save(new Product("notebook", stationery));
        
        locationRepository.save(new Location("CA", "California", 9.75d, Set.of(food)));
        locationRepository.save(new Location("NY", "New York", 8.875d, Set.of(food, clothing)));
        locationRepository.save(new Location("HK", "Hong Kong", 0d, Set.of()));
    }

    
}
