package ru.sicampus.bootcamp2026.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class HourStepTimeValidator implements ConstraintValidator<HourStepTime, Instant> {
    @Override
    public void initialize(HourStepTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Instant value, ConstraintValidatorContext context) {
        ZonedDateTime zoned = value.atZone(ZoneOffset.UTC);
        return zoned.getMinute() == 0 && zoned.getSecond() == 0 && zoned.getNano() == 0;
    }
}
