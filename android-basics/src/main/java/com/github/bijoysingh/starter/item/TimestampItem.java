package com.github.bijoysingh.starter.item;

import android.support.annotation.Nullable;
import android.util.Pair;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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

  /**
   * Private constructor for the TimestampItem
   *
   * @param timestamp the string timestamp
   * @param time      the time string
   * @param date      the date string
   * @param dateTime  the date and time string
   * @param calender  the calendar
   */
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

  /**
   * Builder class for the TimestampItem
   */
  public static class Builder {
    private String timestamp;
    private Integer hours;
    private Integer minutes;
    public String timeFormat;
    public String dateFormat;
    public String dateTimeFormat;

    /**
     * Builder constructor timestamp
     *
     * @param timestamp the timestamp string
     */
    public Builder(String timestamp) {
      this.timestamp = timestamp;
      this.hours = 0;
      this.minutes = 0;
      this.timeFormat = "hh:mm aa";
      this.dateFormat = "dd MMMM yyyy";
      this.dateTimeFormat = "hh:mm aa, dd MMMM yyyy";
    }

    /**
     * Sets the Timezone of the final location
     *
     * @param hours   the hours
     * @param minutes the minutes
     * @return the Builder object
     */
    public Builder setTimezone(Integer hours, Integer minutes) {
      this.hours = hours;
      this.minutes = minutes;
      return this;
    }

    /**
     * Sets the Timezone to the device timezone
     *
     * @return the Builder object
     */
    public Builder setDeviceTimezone() {
      Pair<Integer, Integer> deviceTimezone = getTimezoneOffset();
      return setTimezone(deviceTimezone.first, deviceTimezone.second);
    }

    /**
     * Set the format you want your time to be in.
     *
     * @param timeFormat the format, e.g. default is "hh:mm aa"
     * @return the Builder object
     */
    public Builder setTimeFormat(String timeFormat) {
      this.timeFormat = timeFormat;
      return this;
    }

    /**
     * Set the format you want your date to be in
     *
     * @param dateFormat the format, e.g. default is "dd MMMM yyyy"
     * @return the Builder object
     */
    public Builder setDateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
      return this;
    }

    /**
     * Set the date time format
     *
     * @param dateTimeFormat the date time format, e.g. default is "hh:mm aa, dd MMMM yyyy"
     * @return the Builder object
     */
    public Builder setDateTimeFormat(String dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
      return this;
    }

    /**
     * Gets the joda {@link DateTime} object from the timestamp
     *
     * @param timestamp the timestamp string
     * @return DateTime object, null if it could not parse
     */
    private DateTime getDateTimeObject(String timestamp) {
      try {
        return new DateTime(timestamp);
      } catch (Exception e) {
        return null;
      }
    }

    /**
     * Builder build function
     *
     * @return the {@link TimestampItem} object
     */
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

      SimpleDateFormat dateTimeFormatter =
          new SimpleDateFormat(dateTimeFormat, Locale.getDefault());
      String dateTimeString = dateTimeFormatter.format(calendar.getTime());

      return new TimestampItem(timestamp, time, date, dateTimeString, calendar);
    }

  }

  /**
   * Get the time string
   *
   * @return the time string, timestamp if time is null
   */
  public String getTime() {
    return time == null ? timestamp : time;
  }

  /**
   * Get the date string
   *
   * @return the date string, timestamp if date is null
   */
  public String getDate() {
    return date == null ? timestamp : date;
  }

  /**
   * Get the date and time string
   *
   * @return the date and time string, timestamp if date-time is null
   */
  public String getDateTime() {
    return dateTime == null ? timestamp : dateTime;
  }

  /**
   * Gets the calendar object
   *
   * @return the calendar object
   */
  public Calendar getCalender() {
    return calender;
  }

  /**
   * Gets the compressed date time object. Returns just time if the date is same.
   *
   * @return the date time string.
   */
  public String getCompressedDateTime() {
    Calendar calendar = Calendar.getInstance();
    if (calendar.get(Calendar.DAY_OF_YEAR) == this.calender.get(Calendar.DAY_OF_YEAR)
        && calendar.get(Calendar.YEAR) == this.calender.get(Calendar.YEAR)) {
      return time;
    } else {
      return date;
    }
  }

  /**
   * Get the timezone hours and minutes for your current device timezone
   *
   * @return Pair(hour, minutes)
   */
  public static Pair<Integer, Integer> getTimezoneOffset() {
    TimeZone timeZone = Calendar.getInstance().getTimeZone();
    int mGMTOffset = timeZone.getRawOffset();
    Integer minutes = mGMTOffset / 60000;
    Integer hours = minutes / 60;
    minutes -= hours * 60;
    return new Pair<>(hours, minutes);
  }

}
