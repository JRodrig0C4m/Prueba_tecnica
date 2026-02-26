package com.example.juanrodrigo_camachoperez.validation.annotations;

import com.example.juanrodrigo_camachoperez.validation.validators.RfcTaxIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RfcTaxIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRfcTaxId {
    String message() default "tax_id must have RFC format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
