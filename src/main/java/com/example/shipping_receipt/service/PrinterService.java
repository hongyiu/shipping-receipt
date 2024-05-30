package com.example.shipping_receipt.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.shipping_receipt.component.TaxComponent;
import com.example.shipping_receipt.dto.CartProduct;
import com.example.shipping_receipt.dto.ReceiptItem;
import com.example.shipping_receipt.dto.ReceiptRequest;
import com.example.shipping_receipt.dto.ReceiptResponse;
import com.example.shipping_receipt.entity.Location;
import com.example.shipping_receipt.entity.Product;

@Service
public class PrinterService {

    private final ProductService productService;
    private final LocationService locationService;
    private final TaxComponent taxComponent;

    public PrinterService(ProductService productService, LocationService locationService, TaxComponent taxComponent) {
        this.productService = productService;
        this.locationService = locationService;
        this.taxComponent = taxComponent;
    }

    public ReceiptResponse createReceipt(ReceiptRequest receiptRequest) {
        Location location = locationService.findById(receiptRequest.location());
        
        Set<ReceiptItem> receiptProductSet = receiptRequest.products().stream().map(product -> createReceiptProduct(product, location)).collect(Collectors.toSet());

        return createReceiptResponse(receiptProductSet);
    }
    
    private ReceiptItem createReceiptProduct(CartProduct cartProduct, Location location) {

        final Product product = productService.findByName(cartProduct.name());

        final BigDecimal price = BigDecimal.valueOf(cartProduct.price());
        final BigDecimal tax = taxComponent.calculateTax(product, price, location);

        return new ReceiptItem(
            product.getName(),
            cartProduct.quantity(),
            price,
            tax
            );
    }
        
    private ReceiptResponse createReceiptResponse(Set<ReceiptItem> receiptProductSet) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        for(ReceiptItem receiptProduct : receiptProductSet) {
            BigDecimal quantity = BigDecimal.valueOf(receiptProduct.quantity());
            subtotal = subtotal.add(receiptProduct.price().multiply(quantity));
            tax = tax.add(receiptProduct.tax().multiply(quantity));
        }

        return new ReceiptResponse(receiptProductSet, subtotal, tax, subtotal.add(tax));
    }
        
}
