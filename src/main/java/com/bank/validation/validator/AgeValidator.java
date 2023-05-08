package com.bank.validation.validator;

import com.bank.validation.annotation.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, Byte> {
    private Age annotation;

    @Override
    public void initialize(final Age annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final Byte value, final ConstraintValidatorContext context) {
        return value == null || (value >= annotation.min() && value <= annotation.max());
    }
}
