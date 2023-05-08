package com.bank.validation.validator;

import com.bank.validation.annotation.AccountNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class AccountNumberValidator implements ConstraintValidator<AccountNumber, String> {
    private AccountNumber annotation;

    @Override
    public void initialize(final AccountNumber annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value == null
            || (Pattern.matches(String.format("^\\d{%d}$", annotation.length()), value) && Long.parseLong(value) > 0);
    }
}
