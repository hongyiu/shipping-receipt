package com.example.shipping_receipt.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping_receipt.component.RoundComponent.IncrementMustBeGreaterThanZeroException;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@SpringBootTest
class RoundComponentTest {

    private final RoundComponent roundComponent;

    @Autowired
    public RoundComponentTest(RoundComponent roundComponent) {
        this.roundComponent = roundComponent;
    }

    @ParameterizedTest
    @CsvSource({
        "1.13, 0.05, 1.15",
        "1.15, 0.05, 1.15",
        "1.23, 0.05, 1.25",
        "1.23, 0.1, 1.3",
        "1.23, 0.01, 1.23",
        "1.23, 1, 2",
        "-1.23, 0.05, -1.20",
        "-1.23, 0.1, -1.2",
        "-1.23, 0.01, -1.23",
        "-1.23, 1, -1",
        "0, 0.05, 0",
        "0, 1, 0",
        "4.9E-324, 0.01, 0.01"
    })
    void testRoundUpToNearestIncrement(String value, String increment, String expected) {
        BigDecimal result = roundComponent.roundUpToNearestIncrement(new BigDecimal(value), new BigDecimal(increment));
        assertEquals(new BigDecimal(expected), result);
    }

    @Test
    void shouldThrowValueOutOfRangeException() {
        assertThrows(RoundComponent.ValueOutOfRangeException.class, () -> {
            roundComponent.roundUpToNearestIncrement(BigDecimal.valueOf(Double.MAX_VALUE), new BigDecimal("0.01"));
        });
    }

    @Test
    void shouldThrowIncrementOutOfRangeException() {
        assertThrows(RoundComponent.IncrementOutOfRangeException.class, () -> {
            roundComponent.roundUpToNearestIncrement(BigDecimal.ONE, BigDecimal.valueOf(Double.MAX_VALUE));
        });
    }

    @Test
    void shouldThrowIncrementOutOfRangeExceptionForMinValue() {
        assertThrows(RoundComponent.IncrementOutOfRangeException.class, () -> {
            roundComponent.roundUpToNearestIncrement(BigDecimal.ONE, BigDecimal.valueOf(Double.MIN_VALUE));
        });
    }

    @Test
    void shouldThrowIncrementMustBeGreaterThanZeroException() {
        assertThrows(IncrementMustBeGreaterThanZeroException.class, () -> {
            roundComponent.roundUpToNearestIncrement(BigDecimal.ONE, BigDecimal.ZERO);
        });
    }

}
