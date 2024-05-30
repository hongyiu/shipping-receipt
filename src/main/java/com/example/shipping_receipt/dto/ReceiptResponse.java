package com.example.shipping_receipt.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ReceiptResponse(Set<ReceiptItem> items, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
}