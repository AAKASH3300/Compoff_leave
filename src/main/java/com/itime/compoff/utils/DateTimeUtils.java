package com.itime.compoff.utils;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateTimeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);
    private static final String JSON_DATEONLY_FORMAT = "yyyyMMdd";
    private static final String LONG_TIMESTAMP_FORMATE = "yyyyMMddhhmmss";
    private static final String TIME_STAMPFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PROPER_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_MOTNTH_YEAR = "dd-MMM-yyyy";
    private static final String DATE_MOTNTH_YEAS = "dd-MM-yyyy";
    private static final String TIME = "HH:mm";
    private static final String HOUR = "HH";
    private static final String MIN = "mm";
    private static final String SECOND = "ss";
    private static final String TIME_VALUE = "HH:mm:ss";
    private static final String DATE = "dd/MM/yyyy";
    private static final String HOUR_MIN = "HH:mm";
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

    public static Time getInTime() {
        return Time.valueOf(LocalTime.MIN);

    }

    public static Time getOutTime() {
        return Time.valueOf(LocalTime.NOON);

    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static Timestamp convertFromJsonSqlDateOnly(final String jsonDate) {
        if (StringUtils.isNotBlank(jsonDate)) {
            Date date = convertFromJsonDateOnly(jsonDate);
            return null == date ? null : new Timestamp(date.getTime());
        } else {
            return null;
        }

    }

    public static Date convertFromJsonDateOnly(final String jsonDate) {
        try {
            return StringUtils.isNotBlank(jsonDate)
                    ? new SimpleDateFormat(DateTimeUtils.JSON_DATEONLY_FORMAT).parse(jsonDate)
                    : null;
        } catch (final ParseException e) {
            LOGGER.warn(String.valueOf(e));
            return null;
        }
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static String convertToJsonTimestampDateOnly(final Timestamp timestamp) {
        return timestamp != null ? new SimpleDateFormat(DateTimeUtils.JSON_DATEONLY_FORMAT).format(timestamp) : null;
    }
}
