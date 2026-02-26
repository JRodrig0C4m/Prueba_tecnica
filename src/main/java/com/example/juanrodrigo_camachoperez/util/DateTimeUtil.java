package com.example.juanrodrigo_camachoperez.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Madagascar TZ: Indian/Antananarivo
 * Format required: dd-MM-yyyy HH:mm
 */
public final class DateTimeUtil {
    private DateTimeUtil() {}

    public static final ZoneId MADAGASCAR_TZ = ZoneId.of("Indian/Antananarivo");
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static LocalDateTime nowMadagascarLocalDateTime() {
        return ZonedDateTime.now(MADAGASCAR_TZ).toLocalDateTime();
    }

    public static String formatMadagascar(LocalDateTime ldt) {
        if (ldt == null) return null;
        return ldt.format(FORMATTER);
    }
}
