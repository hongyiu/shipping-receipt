package com.example.shipping_receipt.dto;

import java.math.BigDecimal;

public record ReceiptItem(String name, int quantity, BigDecimal price, BigDecimal tax) {
}