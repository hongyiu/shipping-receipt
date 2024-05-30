package com.example.shipping_receipt.component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class RoundComponent {

    public BigDecimal roundToNearestIncrement(final BigDecimal value, final BigDecimal increment) {
        if (increment.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Increment must be greater than zero");
        }
        final BigDecimal divided = value.divide(increment, 0, RoundingMode.HALF_UP);
        final BigDecimal roundedValue = divided.multiply(increment).setScale(increment.scale(), RoundingMode.HALF_UP);
        
        if (isLessThan(value, roundedValue)) {
            return roundedValue;
        } else {
            return roundedValue.add(increment).setScale(increment.scale(), RoundingMode.HALF_UP);
        }
    }

    private boolean isLessThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    public class IncrementMustBeGreaterThanZeroException extends RuntimeException {
        protected IncrementMustBeGreaterThanZeroException() {
            super("Increment must be greater than zero");
        }
    }

}
