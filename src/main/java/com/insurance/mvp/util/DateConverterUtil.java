package com.insurance.mvp.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.time.*;
import java.util.Date;

public class DateConverterUtil {

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate extractDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }

    public static LocalTime extractTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    public static String toJalaliDate(LocalDate gregorianDate) {
        Calendar persianCalendar = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        persianCalendar.set(gregorianDate.getYear(), gregorianDate.getMonthValue() - 1, gregorianDate.getDayOfMonth());

        int year = persianCalendar.get(Calendar.YEAR);
        int month = persianCalendar.get(Calendar.MONTH) + 1;
        int day = persianCalendar.get(Calendar.DAY_OF_MONTH);

        return String.format("%04d/%02d/%02d", year, month, day);
    }

    public static String toJalaliDateTime(LocalDateTime gregorianDateTime) {
        LocalDate date = gregorianDateTime.toLocalDate();
        LocalTime time = gregorianDateTime.toLocalTime();

        return toJalaliDate(date) + " " + time.toString();
    }

    public static String toJalaliDate(Date date) {
        return toJalaliDate(toLocalDateTime(date).toLocalDate());
    }

    public static String toJalaliDateTime(Date date) {
        return toJalaliDateTime(toLocalDateTime(date));
    }
}