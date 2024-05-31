package com.example.shipping_receipt.component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class RoundComponent {

    public BigDecimal roundUpToNearestIncrement(final BigDecimal value, final BigDecimal increment) {
        if (increment.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncrementMustBeGreaterThanZeroException();
        }

        final BigDecimal maxValue = new BigDecimal("1E+100");
        if (value.compareTo(maxValue) > 0) {
            throw new ValueOutOfRangeException(maxValue);
        }

        final BigDecimal maxIncrement = new BigDecimal("1E+50");
        final BigDecimal minIncrement = new BigDecimal("1E-50");
        if (increment.compareTo(maxIncrement) > 0 || increment.compareTo(minIncrement) < 0) {
            throw new IncrementOutOfRangeException(minIncrement, maxIncrement);
        }

        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return value;
        }

        final BigDecimal divided = value.divide(increment, 0, RoundingMode.CEILING);
        final BigDecimal roundedValue = divided.multiply(increment);

        return roundedValue.setScale(increment.scale());

    }

    public class IncrementMustBeGreaterThanZeroException extends RuntimeException {
        protected IncrementMustBeGreaterThanZeroException() {
            super("Increment must be greater than zero");
        }
    }

    public class ValueOutOfRangeException extends RuntimeException {
        protected ValueOutOfRangeException(BigDecimal maxValue) {
            super("Value is out of range. It should be less than " + maxValue);
        }
    }
    
    public class IncrementOutOfRangeException extends RuntimeException {
        protected IncrementOutOfRangeException(BigDecimal minIncrement, BigDecimal maxIncrement) {
            super("Increment is out of range. It should be between " + minIncrement + " and " + maxIncrement);
        }
    }

}
