package com.example.shipping_receipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shipping_receipt.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
