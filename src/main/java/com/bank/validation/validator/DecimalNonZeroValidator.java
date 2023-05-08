package com.bank.validation.validator;

import com.bank.validation.annotation.DecimalNonZero;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class DecimalNonZeroValidator implements ConstraintValidator<DecimalNonZero, BigDecimal> {
    @Override
    public void initialize(final DecimalNonZero annotation) {
    }

    @Override
    public boolean isValid(final BigDecimal value, final ConstraintValidatorContext context) {
        return value == null || BigDecimal.ZERO.compareTo(value) != 0;
    }
}
