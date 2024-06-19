package com.itime.compoff.utils;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    private static final String JSON_DATE_ONLY_FORMAT = "yyyyMMdd";

    private static final LocalDate now = LocalDate.now();
    private static final LocalDate firstDay = now.with(firstDayOfYear());
    private static final LocalDate lastDay = now.with(lastDayOfYear());

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
        return timestamp != null ? new SimpleDateFormat(DateTimeUtils.JSON_DATE_ONLY_FORMAT).format(timestamp) : null;
    }

    public static String formatTimestampToDateString(final Timestamp timestamp) {
        LocalDate date = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("d'th' MMM EEEE"));
    }

    public static Date convertFromJsonDateOnly(final String jsonDate) {
        try {
            return StringUtils.isNotBlank(jsonDate)
                    ? new SimpleDateFormat(DateTimeUtils.JSON_DATE_ONLY_FORMAT).parse(jsonDate)
                    : null;
        } catch (final ParseException e) {
            LOGGER.warn(AppConstants.PARSER_EXCEPTION, e);
            return null;
        }
    }
}
