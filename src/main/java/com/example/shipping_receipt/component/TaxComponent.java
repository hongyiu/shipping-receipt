package com.example.shipping_receipt.component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.entity.Product;

@Component
public class TaxComponent {

    public final RoundComponent roundComponent;

    public TaxComponent(RoundComponent roundComponent) {
        this.roundComponent = roundComponent;
    }

    public BigDecimal calculateTax(Product product, BigDecimal price, Location location) {
    
        final boolean isTexExempt = product.getCategory().getTaxExemptLocations().contains(location);
        if(isTexExempt) {
            return BigDecimal.ZERO;
        }
    
        final BigDecimal taxRate = BigDecimal.valueOf(location.getTaxRate()).divide(new BigDecimal("100")); 
        final BigDecimal roundedTaxRate = roundComponent.roundToNearestIncrement(taxRate, BigDecimal.valueOf(0.0005));
        
        return price.multiply(roundedTaxRate).setScale(2, RoundingMode.HALF_UP);
        
    }

}
