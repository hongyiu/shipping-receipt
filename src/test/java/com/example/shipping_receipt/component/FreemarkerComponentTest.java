package com.example.shipping_receipt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping_receipt.dto.ReceiptItem;
import com.example.shipping_receipt.dto.ReceiptResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SpringBootTest
class FreemarkerComponentTest {

    private final FreemarkerComponent freemarkerComponent;

    @Autowired
    FreemarkerComponentTest(FreemarkerComponent freemarkerComponent) {
        this.freemarkerComponent = freemarkerComponent;
    }

    @Test
    void givenCorrectTemplateName_whenProcessTemplateIntoString_thenReturnStringCorrectly() {
        final String productName = "shirt";
        final String quantity = "1";
        final String productPrice = "100.00";
        final String productTax = "0.01";
        final String total = "100.01";

        final ReceiptItem receiptItem = new ReceiptItem(productName, Integer.parseInt(quantity), new BigDecimal(productPrice), new BigDecimal(productTax));
        final ReceiptResponse receiptResponse = new ReceiptResponse(Set.of(receiptItem), new BigDecimal(productPrice), new BigDecimal(productTax), new BigDecimal(total));

        final String resultString = freemarkerComponent.processTemplateIntoString("receipt.ftl", Map.of("receipt", receiptResponse));
                    
        Assertions.assertTrue(resultString.contains(productName), "Result string should contain the product name");
        Assertions.assertTrue(resultString.contains("1"), "Result string should contain quantity");
        assertTrue(resultString.contains(productPrice), "Result string should contain the product price");
        assertTrue(resultString.contains("subtotal:"), "Result string should contain subtotal");
        assertTrue(resultString.contains("tax:"), "Result string should contain tax");
        assertTrue(resultString.contains(productTax), "Result string should contain the tax amount");
        assertTrue(resultString.contains("total:"), "Result string should contain total");
        assertTrue(resultString.contains(total), "Result string should contain the total amount");
    }

    @Test
    void givenIncorrectTemplateName_whenProcessTemplateIntoString_thenThrowException() {
        final String productName = "shirt";
        final String quantity = "1";
        final String productPrice = "100.00";
        final String productTax = "0.01";
        final String total = "100.01";

        final ReceiptItem receiptItem = new ReceiptItem(productName, Integer.parseInt(quantity), new BigDecimal(productPrice), new BigDecimal(productTax));
        final ReceiptResponse receiptResponse = new ReceiptResponse(Set.of(receiptItem), new BigDecimal(productPrice), new BigDecimal(productTax), new BigDecimal(total));

        Assertions.assertThrows(FreemarkerComponent.ProcessTemplateException.class, () -> {
            freemarkerComponent.processTemplateIntoString("incorrect.ftl", Map.of("receipt", receiptResponse));
        });
    }
    
    @Test
    void givenIncorrectModel_whenProcessTemplateIntoString_thenThrowException() {
        Assertions.assertThrows(FreemarkerComponent.ProcessTemplateException.class, () -> {
            freemarkerComponent.processTemplateIntoString("receipt.ftl", Map.of());
        });
    }

}
