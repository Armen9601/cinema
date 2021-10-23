package com.example.common.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    private static final String FORMAT_WITH_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_WITH_DATE = "yyyy-MM-dd";

    public static DateTimeFormatter dateTimeFormatterWithDateAndTime() {
        return DateTimeFormatter.ofPattern(FORMAT_WITH_DATE_AND_TIME);
    }

    public static DateTimeFormatter dateTimeFormatterWithDate() {
        return DateTimeFormatter.ofPattern(FORMAT_WITH_DATE);
    }

}
