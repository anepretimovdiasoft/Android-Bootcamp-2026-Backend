package ru.sicampus.bootcamp2026.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HourStepTime {
    String message() default "Timestamp has precision more than hourly";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

