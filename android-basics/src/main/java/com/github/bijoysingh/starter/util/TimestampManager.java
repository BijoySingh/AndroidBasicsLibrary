package com.github.bijoysingh.starter.util;

import com.github.bijoysingh.starter.item.TimestampItem;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;

/**
 * Timestamp functions
 * Created by Bijoy on 5/25/2015.
 */
public class TimestampManager {
    public static String SPACE = " ";
    public static String PM = "p.m.";
    public static String AM = "a.m.";
    public static String MIDNIGHT = "Midnight";
    public static String NOON = "Noon";
    public static String COLON = ":";

    /**
     * Get the datetime object from timestamp else returns null
     *
     * @param timestamp the timestamp
     * @return DateTime object or null
     */
    private static DateTime getDateTimeObject(String timestamp) {
        try {
            return new DateTime(timestamp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates th timestamp item from the datetime variable
     *
     * @param timestamp the string input timestamp
     * @param dateTime  the date time instance
     * @return the TimestampItem object
     */
    private static TimestampItem createTimestampItem(String timestamp, DateTime dateTime) {
        String MM = dateTime.monthOfYear().getAsString();
        String MMMM = dateTime.monthOfYear().getAsText();
        String dd = dateTime.dayOfMonth().getAsText();
        String hh = dateTime.hourOfDay().getAsText();
        String mm = dateTime.minuteOfHour().getAsText();
        String yyyy = dateTime.year().getAsText();

        String date = dd + SPACE + MMMM + SPACE + yyyy;
        String time = get12hrFormattedTime(hh, mm);
        Calendar calender = getCalendar(yyyy, MM, dd, hh, mm);
        return new TimestampItem(timestamp, time, date, calender);
    }

    /**
     * Returns a TimestampItem object by parsing the time using DateTime library
     *
     * @param timestamp the timestamp object
     * @return the TimestampItem
     */
    public static TimestampItem getTimestampItem(String timestamp) {
        return getTimestampItem(timestamp, DateTimeZone.UTC, 0, 0);
    }

    /**
     * Returns a TimestampItem object by parsing the time using DateTime library
     *
     * @param timestamp the timestamp object
     * @param hour      the timezone hours to be added
     * @param minutes   the timezone minutes to be added
     * @return the TimestampItem
     */
    public static TimestampItem getTimestampItem(String timestamp,
                                                 Integer hour, Integer minutes) {
        return getTimestampItem(timestamp, DateTimeZone.UTC, hour, minutes);
    }

    /**
     * Returns a TimestampItem object by parsing the time using DateTime library
     *
     * @param timestamp    the timestamp object
     * @param dateTimeZone the date timezone of the input time.
     * @param hour         the timezone hours to be added
     * @param minutes      the timezone minutes to be added
     * @return the TimestampItem
     */
    public static TimestampItem getTimestampItem(String timestamp,
                                                 DateTimeZone dateTimeZone,
                                                 Integer hour, Integer minutes) {
        DateTime dateTime = getDateTimeObject(timestamp);
        if (dateTime == null) {
            return new TimestampItem(timestamp, null, null, Calendar.getInstance());
        }

        dateTime = dateTime.toDateTime(dateTimeZone);
        dateTime = dateTime.plusHours(5).plusMinutes(30);

        return createTimestampItem(timestamp, dateTime);
    }

    /**
     * Returns 12 hr format for hr and minutes in 24 hrs
     *
     * @param hh the hours
     * @param mm the minutes
     * @return the 12-hr formatted string
     */
    public static String get12hrFormattedTime(String hh, String mm) {
        Integer hr = Integer.parseInt(hh);
        Integer min = Integer.parseInt(mm);

        if (mm.length() == 1)
            mm = "0" + mm;

        if (hr == 0 && min == 0) {
            return MIDNIGHT;
        } else if (hr == 0) {
            return 12 + COLON + mm + SPACE + AM;
        } else if (hr < 12) {
            return hh + COLON + mm + SPACE + AM;
        } else if (hr == 12 && min == 0) {
            return NOON;
        } else {
            return (hr - 12) + COLON + mm + SPACE + PM;
        }
    }

    /**
     * Creates the calendar object from the various time values
     *
     * @param yyyy year
     * @param MM   month
     * @param dd   date
     * @param hh   hour
     * @param mm   minutes
     * @return the Calendar value
     */
    private static Calendar getCalendar(
        String yyyy,
        String MM,
        String dd,
        String hh,
        String mm
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
            Integer.parseInt(yyyy),
            Integer.parseInt(MM) - 1,
            Integer.parseInt(dd),
            Integer.parseInt(hh),
            Integer.parseInt(mm)
        );
        return calendar;
    }

}
