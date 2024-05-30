package com.example.shipping_receipt.dto;

import java.util.Set;

public record ReceiptRequest(Set<CartProduct> products, String location) {
    
}
