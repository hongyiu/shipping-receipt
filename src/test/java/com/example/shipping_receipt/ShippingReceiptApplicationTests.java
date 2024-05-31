package com.example.shipping_receipt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping_receipt.controller.PrinterController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShippingReceiptApplicationTests {

	private final PrinterController printerController;
	
	@Autowired
	public ShippingReceiptApplicationTests(PrinterController printerController) {
		this.printerController = printerController;
	}

	@Test
	void contextLoads() {
		assertThat(printerController).isNotNull();
	}

}
