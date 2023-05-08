package com.bank.validation.validator;

import com.bank.validation.annotation.IdNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IdNumberValidator implements ConstraintValidator<IdNumber, String> {
    @Override
    public void initialize(final IdNumber annotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value == null || (Pattern.matches("^\\d{10}$", value) && Long.parseLong(value) > 0);
    }
}
