package com.example.juanrodrigo_camachoperez.validation.validators;

import com.example.juanrodrigo_camachoperez.validation.annotations.ValidAndresPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AndresPhoneValidator implements ConstraintValidator<ValidAndresPhone, String> {

    /**
     * PDF no define "AndresFormat". Implementación mínima:
     * - Normaliza a dígitos (permite +CC, espacios, guiones, paréntesis)
     * - Usa los últimos 10 dígitos como teléfono local
     * - Rechaza:
     *   - todos iguales (0000000000)
     *   - secuencia ascendente (0123456789)
     *   - secuencia descendente (9876543210)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        String digits = value.replaceAll("\\D", "");
        if (digits.length() < 10) return false;

        String last10 = digits.substring(digits.length() - 10);
        if (!last10.matches("^\\d{10}$")) return false;

        boolean allSame = true;
        for (int i = 1; i < last10.length(); i++) {
            if (last10.charAt(i) != last10.charAt(0)) { allSame = false; break; }
        }
        if (allSame) return false;

        if ("0123456789".equals(last10)) return false;
        if ("9876543210".equals(last10)) return false;

        return true;
    }
}
