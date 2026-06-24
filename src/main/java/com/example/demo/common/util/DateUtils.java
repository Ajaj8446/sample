package com.example.demo.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_DATETIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final ZoneId UTC = ZoneId.of("UTC");

    private DateUtils() {
    }

    public static Instant now() {
        return Instant.now();
    }

    public static LocalDate today() {
        return LocalDate.now(UTC);
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(UTC);
    }

    public static String formatDate(LocalDate date) {
        return date == null ? null : date.format(ISO_DATE);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(ISO_DATETIME);
    }

    public static LocalDate parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return LocalDate.parse(dateStr, ISO_DATE);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (StringUtils.isBlank(dateTimeStr)) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, ISO_DATETIME);
    }

    public static long toEpochMillis(Instant instant) {
        return instant == null ? 0L : instant.toEpochMilli();
    }

    public static Instant fromEpochMillis(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis);
    }
}
