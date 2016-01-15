package com.birdlabs.starter.util;

import com.birdlabs.starter.item.TimestampItem;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Timestamp functions
 * Created by Bijoy on 5/25/2015.
 */
public class TimestampManager implements Serializable {
    public static String SPACE = " ";
    public static String PM = "p.m.";
    public static String AM = "a.m.";
    public static String MIDNIGHT = "Midnight";
    public static String NOON = "Noon";
    public static String COLON = ":";

    public static TimestampItem getTimestampItem(String timestamp) {
        DateTime dateTime;
        try {
            dateTime = new DateTime(timestamp);
        } catch (Exception e) {
            return new TimestampItem(timestamp, null, null, Calendar.getInstance());
        }

        dateTime = dateTime.toDateTime(DateTimeZone.UTC);
        dateTime = dateTime.plusHours(5).plusMinutes(30);

        String MM = dateTime.monthOfYear().getAsString();
        String MMMM = dateTime.monthOfYear().getAsText();
        String dd = dateTime.dayOfMonth().getAsText();
        String hh = dateTime.hourOfDay().getAsText();
        String mm = dateTime.minuteOfHour().getAsText();
        String yyyy = dateTime.year().getAsText();

        String date = dd + SPACE + MMMM + SPACE + yyyy;
        String time = getTimeStr(hh, mm);
        Calendar calender = getCalendar(yyyy, MM, dd, hh, mm);

        return new TimestampItem(timestamp, time, date, calender);
    }

    public static String getTimeStr(String hh, String mm) {
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

    public static Calendar getCalendar(
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
