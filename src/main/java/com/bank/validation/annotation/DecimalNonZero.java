package com.bank.validation.annotation;

import com.bank.validation.validator.DecimalNonZeroValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=DecimalNonZeroValidator.class)
public @interface DecimalNonZero {
    String message() default "must be different than 0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
