package com.bank.validation.annotation;

import com.bank.validation.validator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=PhoneValidator.class)
public @interface Phone {
    String message() default "must be a string of {min} to {max} digits long and greater than 0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int min() default 9;

    int max() default 10;
}
