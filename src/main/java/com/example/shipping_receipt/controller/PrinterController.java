package com.example.shipping_receipt.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.example.shipping_receipt.dto.ReceiptRequest;
import com.example.shipping_receipt.dto.ReceiptResponse;
import com.example.shipping_receipt.service.PrinterService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PrinterController {

    private static final Logger log = LoggerFactory.getLogger(PrinterController.class);

    private final PrinterService printerService;
    private final Configuration freemarkerConfig;

    public PrinterController(PrinterService printerService, Configuration freemarkerConfig) {
        this.printerService = printerService;
        this.freemarkerConfig = freemarkerConfig;
    }

    @GetMapping("receipts/print")
    protected String printReceipt(@RequestBody ReceiptRequest receiptRequest) throws TemplateException {

        final ReceiptResponse receipt = printerService.createReceipt(receiptRequest);

        try {
            final Template template = freemarkerConfig.getTemplate("receipt.ftl");
            String receiptString = FreeMarkerTemplateUtils.processTemplateIntoString(template, Map.of("receipt", receipt));
            
            log.info(receiptString);
            return receiptString;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
            throw e;
        }
        
        return null;
    }

}
