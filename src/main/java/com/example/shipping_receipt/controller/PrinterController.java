package com.example.shipping_receipt.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.example.shipping_receipt.component.FreemarkerComponent;
import com.example.shipping_receipt.dto.ReceiptRequest;
import com.example.shipping_receipt.dto.ReceiptResponse;
import com.example.shipping_receipt.service.PrinterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PrinterController {

    private static final Logger log = LoggerFactory.getLogger(PrinterController.class);

    private final PrinterService printerService;
    private final FreemarkerComponent freemarkerComponent;

    public PrinterController(PrinterService printerService, FreemarkerComponent freemarkerComponent) {
        this.printerService = printerService;
        this.freemarkerComponent = freemarkerComponent;
    }

    @GetMapping("receipts/print")
    protected String printReceipt(@RequestBody ReceiptRequest receiptRequest) {

        final ReceiptResponse receipt = printerService.createReceipt(receiptRequest);

        final String receiptString = freemarkerComponent.processTemplateIntoString("receipt.ftl", Map.of("receipt", receipt));

        log.info(receiptString);

        return receiptString;
    }

}
