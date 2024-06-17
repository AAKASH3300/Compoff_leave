package com.itime.compoff.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateTimeUtils {

    private static final String JSON_DATEONLY_FORMAT = "yyyyMMdd";

    private static final String TIME_STAMPFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PROPER_DATE_FORMAT = "yyyy-MM-dd";

    private static final String TIME = "HH:mm";
    private static final String HOUR = "HH";
    private static final String MIN = "mm";
    private static final String SECOND = "ss";
    private static final String TIME_VALUE = "HH:mm:ss";
    private static final String DATE = "dd/MM/yyyy";

    private static LocalDate now = LocalDate.now();
    private static LocalDate firstDay = now.with(firstDayOfYear());
    private static LocalDate lastDay = now.with(lastDayOfYear());

    DateTimeUtils() {
    }

    public static Timestamp getFirstDayOfYear() {
        return Timestamp.valueOf(firstDay.atStartOfDay());
    }

    public static Timestamp getLastDayOfYear() {
        return Timestamp.valueOf(lastDay.atStartOfDay());
    }

    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static LocalDateTime convertTimeToLocalDateTime(Time time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
    }

    public static long findMillisecondsFromHourAndMinute(Integer hour, Integer minute) {
        return (hour * 60 * 60 * 1000) + (minute * 60 * 1000);
    }

    public static String convertToJsonTimestampDateOnly(final Timestamp timestamp) {
        return timestamp != null ? new SimpleDateFormat(DateTimeUtils.JSON_DATEONLY_FORMAT).format(timestamp) : null;
    }
}
