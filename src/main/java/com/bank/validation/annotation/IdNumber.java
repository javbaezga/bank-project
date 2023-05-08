package com.bank.validation.annotation;

import com.bank.validation.validator.IdNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=IdNumberValidator.class)
public @interface IdNumber {
    String message() default "must be a string of 10 digits long and greater than 0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
