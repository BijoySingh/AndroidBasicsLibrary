package com.birdlabs.starter.item;

import java.util.Calendar;

/**
 * item for timestamp
 * Created by bijoy on 1/3/16.
 */
public class TimestampItem {
    public String timestamp, time, date;
    public Calendar calender;

    public TimestampItem(String timestamp, String time, String date, Calendar calender) {
        this.timestamp = timestamp;
        this.time = time;
        this.date = date;
        this.calender = calender;
    }

    public String getTimeString(boolean compressed) {
        if (date == null || time == null) {
            return timestamp;
        }

        if (compressed) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_YEAR) == this.calender.get(Calendar.DAY_OF_YEAR)
                    && calendar.get(Calendar.YEAR) == this.calender.get(Calendar.YEAR)) {
                return time;
            } else {
                return date;
            }
        }
        return time + ", " + date;
    }

}
