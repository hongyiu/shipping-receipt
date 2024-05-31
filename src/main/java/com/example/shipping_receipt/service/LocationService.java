package com.example.shipping_receipt.service;

import org.springframework.stereotype.Service;

import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.repository.LocationRepository;

@Service
public class LocationService {
    
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    
    public Location findById(String id) {
        return locationRepository.findById(id).orElseThrow(() -> new LocationIdNotFoundException(id));
    }

    public class LocationIdNotFoundException extends RuntimeException {
        private final String id;

        protected LocationIdNotFoundException(String id) {
            super("Location not found with id: " + id);
            this.id = id;
        }
        
        public String getId() {
            return id;
        }

    }
}
