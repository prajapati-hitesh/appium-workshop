package com.tribe.workshop.appium.helpers;

import com.tribe.workshop.appium.enums.Month;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateHelper {
    public static Month getMonthValue(Calendar cal) {
        Month bMonthEnum = null;
        String bMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).substring(0, 3);
        Month[] mValues = Month.values();

        for (Month m : mValues) {
            if (m.toString().trim().equalsIgnoreCase(bMonth)) {
                bMonthEnum = m;
                break;
            }
        }
        return bMonthEnum;
    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return (sb.toString());
    }

    public static String getCurrentTimeStampWithFormatAs(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss").format(new Date());
    }

    public static String getCurrentMonth() {
        LocalDate localDate = LocalDate.now();

        /* Methods that can be used
            localDate.getYear() --> This will return year in int. i.e. 2019,2018 etc
            localDate.getDayOfMonth() --> This will return current date. i.e. 1,2,3,4....31
            localDate.getDayOfWeek() --> This will return day of week. i.e. Monday, Tuesday
            localDate.getDayOfYear() --> This will return day of year for current date. i.e. 311th day for date 7th November 2019
            localDate.getMonth().name() --> This will return the current month as string. i.e. JANUARY, FEBRUARY .... DECEMBER
         */

        return localDate.getMonth().name();
    }

    public static int getCurrentDate() {
        LocalDate localDate = LocalDate.now();

        /* Methods that can be used
            localDate.getYear() --> This will return year in int. i.e. 2019,2018 etc
            localDate.getDayOfMonth() --> This will return current date. i.e. 1,2,3,4....31
            localDate.getDayOfWeek() --> This will return day of week. i.e. Monday, Tuesday
            localDate.getDayOfYear() --> This will return day of year for current date. i.e. 311th day for date 7th November 2019
            localDate.getMonth().name() --> This will return the current month as string. i.e. JANUARY, FEBRUARY .... DECEMBER
         */

        return localDate.getDayOfMonth();
    }

    public static int getCurrentYear() {
        LocalDate localDate = LocalDate.now();

        /* Methods that can be used
            localDate.getYear() --> This will return year in int. i.e. 2019,2018 etc
            localDate.getDayOfMonth() --> This will return current date. i.e. 1,2,3,4....31
            localDate.getDayOfWeek() --> This will return day of week. i.e. Monday, Tuesday
            localDate.getDayOfYear() --> This will return day of year for current date. i.e. 311th day for date 7th November 2019
            localDate.getMonth().name() --> This will return the current month as string. i.e. JANUARY, FEBRUARY .... DECEMBER
         */

        return localDate.getYear();
    }

    public static int getCurrentMonthAsNumber() {
        LocalDate localDate = LocalDate.now();

        /* Methods that can be used
            localDate.getYear() --> This will return year in int. i.e. 2019,2018 etc
            localDate.getDayOfMonth() --> This will return current date. i.e. 1,2,3,4....31
            localDate.getDayOfWeek() --> This will return day of week. i.e. Monday, Tuesday
            localDate.getDayOfYear() --> This will return day of year for current date. i.e. 311th day for date 7th November 2019
            localDate.getMonth().name() --> This will return the current month as string. i.e. JANUARY, FEBRUARY .... DECEMBER
         */

        return localDate.getMonthValue();
    }
}
