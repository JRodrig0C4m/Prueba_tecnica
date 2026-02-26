package com.example.juanrodrigo_camachoperez.util;

import com.example.juanrodrigo_camachoperez.exception.BadRequestException;
import com.example.juanrodrigo_camachoperez.model.User;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * Filter format required:
 * [email|id|name|phone|tax_id|created_at]+[co|eq|sw|ew]+[value]
 *
 * Note: '+' can be decoded as space in querystrings. This parser accepts both.
 */
public final class FilteringUtil {
    private FilteringUtil() {}

    public static Predicate<User> parseFilter(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            throw new BadRequestException("filter should not be empty or null");
        }

        String normalized = filter.trim().replace(" ", "+");
        String[] parts = normalized.split("\\+");
        if (parts.length < 3) {
            throw new BadRequestException("filter must have format field+op+value");
        }

        String field = parts[0].trim().toLowerCase(Locale.ROOT);
        String op = parts[1].trim().toLowerCase(Locale.ROOT);

        // value could contain '+', join remaining parts
        StringBuilder sb = new StringBuilder(parts[2]);
        for (int i = 3; i < parts.length; i++) sb.append("+").append(parts[i]);
        String value = sb.toString().trim();

        return user -> {
            String candidate = fieldValue(user, field);
            if (candidate == null) candidate = "";
            String c = candidate.toLowerCase(Locale.ROOT);
            String v = value.toLowerCase(Locale.ROOT);

            return switch (op) {
                case "co" -> c.contains(v);
                case "eq" -> c.equals(v);
                case "sw" -> c.startsWith(v);
                case "ew" -> c.endsWith(v);
                default -> throw new BadRequestException("Invalid filter operation: " + op);
            };
        };
    }

    private static String fieldValue(User u, String field) {
        return switch (field) {
            case "email" -> u.getEmail();
            case "id" -> u.getId() == null ? null : u.getId().toString();
            case "name" -> u.getName();
            case "phone" -> u.getPhone();
            case "tax_id", "taxid" -> u.getTaxId();
            case "created_at", "createdat" -> DateTimeUtil.formatMadagascar(u.getCreatedAt());
            default -> throw new BadRequestException("Invalid filter field: " + field);
        };
    }
}
