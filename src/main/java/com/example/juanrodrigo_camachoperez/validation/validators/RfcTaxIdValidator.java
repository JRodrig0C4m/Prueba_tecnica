package com.example.juanrodrigo_camachoperez.validation.validators;

import com.example.juanrodrigo_camachoperez.validation.annotations.ValidRfcTaxId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class RfcTaxIdValidator implements ConstraintValidator<ValidRfcTaxId, String> {

    // RFC básico: 3-4 letras (incluye Ñ &), fecha YYMMDD y homoclave 3 alfanum
    //Evitamos \d para no tener problemas de escape en Java strings.
    private static final Pattern RFC =
            Pattern.compile("^([A-ZÑ&]{3,4})[0-9]{6}([A-Z0-9]{3})$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotBlank cubre requerido
        String v = value.trim().toUpperCase();
        return RFC.matcher(v).matches();
    }
}