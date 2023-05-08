package com.bank.validation.validator;

import com.bank.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private Phone annotation;

    @Override
    public void initialize(final Phone annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value == null
            || (Pattern.matches(String.format("^\\d{%d,%d}$", annotation.min(), annotation.max()), value) && Long.parseLong(value) > 0);
    }
}
