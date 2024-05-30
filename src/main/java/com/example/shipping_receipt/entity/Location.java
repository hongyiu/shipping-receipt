package com.example.shipping_receipt.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
        
    @Id
    private String id;
    private String name;
    @Column(name = "tax_rate")
    private double taxRate;
    @ManyToMany
    @JoinTable(
        name = "tax_exempt_category",
        joinColumns = @JoinColumn(name = "location_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> taxExemptCategories;

    public Location() {
    }

    public Location(String id, String name, double taxRate, Set<Category> taxExemptCategories) {
        this.id = id;
        this.name = name;
        this.taxRate = taxRate;
        this.taxExemptCategories = taxExemptCategories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Set<Category> getTaxExemptCategories() {
        return taxExemptCategories;
    }

    public void setTaxExemptCategories(Set<Category> taxExemptCategories) {
        this.taxExemptCategories = taxExemptCategories;
    }
    
}
