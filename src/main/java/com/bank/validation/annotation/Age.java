package com.bank.validation.annotation;

import com.bank.validation.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=AgeValidator.class)
public @interface Age {
    String message() default "must be a number between {min} to {max}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    byte min() default 13;

    byte max() default 115;
}
