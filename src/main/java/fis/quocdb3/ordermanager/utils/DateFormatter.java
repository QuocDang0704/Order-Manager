package fis.quocdb3.ordermanager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDateTime toDate(final String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public static String toString(final LocalDateTime date){
        return date.format(formatter);
    }
}
