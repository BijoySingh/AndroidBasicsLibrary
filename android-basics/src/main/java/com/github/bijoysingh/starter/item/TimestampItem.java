package com.github.bijoysingh.starter.item;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * item for timestamp
 * Created by bijoy on 1/3/16.
 */
public class TimestampItem {
    private String timestamp;
    private String time;
    private String date;
    private String dateTime;
    private Calendar calender;

    private TimestampItem(String timestamp,
                          @Nullable String time,
                          @Nullable String date,
                          @Nullable String dateTime,
                          Calendar calender) {
        this.timestamp = timestamp;
        this.time = time;
        this.date = date;
        this.dateTime = dateTime;
        this.calender = calender;
    }

    public static class Builder {
        private String timestamp;
        private Integer hours;
        private Integer minutes;
        public String timeFormat;
        public String dateFormat;
        public String dateTimeFormat;

        public Builder (String timestamp) {
            this.timestamp = timestamp;
            this.hours = 0;
            this.minutes = 0;
            this.timeFormat = "hh:mm aa";
            this.dateFormat = "dd MMMM yyyy";
            this.dateTimeFormat = "hh:mm aa, dd MMMM yyyy";
        }

        public Builder setTimezone(Integer hours, Integer minutes) {
            this.hours = hours;
            this.minutes = minutes;
            return this;
        }

        public Builder setTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
            return this;
        }

        public Builder setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        public Builder setDateTimeFormat(String dateTimeFormat) {
            this.dateTimeFormat = dateTimeFormat;
            return this;
        }

        private DateTime getDateTimeObject(String timestamp) {
            try {
                return new DateTime(timestamp);
            } catch (Exception e) {
                return null;
            }
        }

        public TimestampItem build() {
            DateTime dateTime = getDateTimeObject(timestamp);
            if (dateTime == null) {
                return new TimestampItem(timestamp, null, null, null, Calendar.getInstance());
            }

            dateTime = dateTime.toDateTime(DateTimeZone.UTC);
            dateTime = dateTime.plusHours(hours).plusMinutes(minutes);
            Calendar calendar = dateTime.toCalendar(Locale.getDefault());

            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
            String date = dateFormatter.format(calendar.getTime());

            SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat, Locale.getDefault());
            String time = timeFormatter.format(calendar.getTime());

            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(dateTimeFormat, Locale.getDefault());
            String dateTimeString = dateTimeFormatter.format(calendar.getTime());

            return new TimestampItem(timestamp, time, date, dateTimeString, calendar);
        }

    }

    public String getTime() {
        return time == null ? timestamp : time;
    }

    public String getDate() {
        return date == null ? timestamp : date;
    }

    public String getDateTime() {
        return dateTime == null ? timestamp : dateTime;
    }

    public Calendar getCalender() {
        return calender;
    }

    public String getCompressedDateTime() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_YEAR) == this.calender.get(Calendar.DAY_OF_YEAR)
            && calendar.get(Calendar.YEAR) == this.calender.get(Calendar.YEAR)) {
            return time;
        } else {
            return date;
        }
    }

}
