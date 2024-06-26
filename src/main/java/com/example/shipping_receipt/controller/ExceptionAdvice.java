package com.example.shipping_receipt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.shipping_receipt.component.RoundComponent.IncrementMustBeGreaterThanZeroException;
import com.example.shipping_receipt.component.RoundComponent.IncrementOutOfRangeException;
import com.example.shipping_receipt.component.RoundComponent.ValueOutOfRangeException;
import com.example.shipping_receipt.service.LocationService.LocationIdNotFoundException;
import com.example.shipping_receipt.service.ProductService.ProductNameNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNameNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    @ExceptionHandler(LocationIdNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationIdNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }
    
    @ExceptionHandler(IncrementMustBeGreaterThanZeroException.class)
    public ResponseEntity<String> handleIncrementMustBeGreaterThanZeroException(IncrementMustBeGreaterThanZeroException e) {
        log.error("Increment must be greater than zero", e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(ValueOutOfRangeException.class)
    public ResponseEntity<String> handleValueOutOfRangeException(ValueOutOfRangeException e) {
        log.error("Value is out of range", e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(IncrementOutOfRangeException.class)
    public ResponseEntity<String> handleIncrementOutOfRangeException(IncrementOutOfRangeException e) {
        log.error("Increment is out of range", e);
        return ResponseEntity.internalServerError().build();
    }

}
