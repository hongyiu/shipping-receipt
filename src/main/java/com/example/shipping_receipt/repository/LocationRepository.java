package com.example.shipping_receipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shipping_receipt.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

}
