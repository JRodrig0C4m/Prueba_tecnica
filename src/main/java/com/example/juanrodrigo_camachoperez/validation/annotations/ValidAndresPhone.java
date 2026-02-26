package com.example.juanrodrigo_camachoperez.validation.annotations;

import com.example.juanrodrigo_camachoperez.validation.validators.AndresPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AndresPhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAndresPhone {
    String message() default "phone must be 10 digits (country code allowed) and pass AndresFormat";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
