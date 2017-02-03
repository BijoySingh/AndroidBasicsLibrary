package com.github.bijoysingh.starter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Simple function and variables to format time
 * Created by bijoy on 1/27/17.
 */

public class DateFormatter {
  /**
   * Some common formats to quicken usages
   */
  public enum Formats {
    DD_MM_YYYY("dd MM yyyy"),
    DD_MMM_YYYY("dd MMM yyyy"),
    DD_MMMM_YYYY("dd MMM yyyy"),
    HH_MM_SS("HH:mm.ss"),
    HH_MM("HH:mm"),
    HH_MM_A("hh:mm a"),
    HH_MM_SS_A("hh:mm.ss a"),
    HH_MM_DD_MM_YYYY("HH:mm, dd MM yyyy"),
    HH_MM_DD_MMM_YYYY("HH:mm, dd MMM yyyy"),
    HH_MM_DD_MMMM_YYYY("HH:mm, dd MMMM yyyy"),
    HH_MM_A_DD_MM_YYYY("hh:mm a, dd MM yyyy"),
    HH_MM_A_DD_MMM_YYYY("hh:mm a, dd MMM yyyy"),
    HH_MM_A_DD_MMMM_YYYY("hh:mm a, dd MMMM yyyy");

    private String format;

    Formats(String format) {
      this.format = format;
    }

    public String getFormat() {
      return format;
    }
  }

  private static String getDefaultFormat() {
    return Formats.HH_MM_A_DD_MMMM_YYYY.getFormat();
  }

  /**
   * Convert today's date to String
   *
   * @return the formatted date string
   */
  public static String getToday() {
    return getToday(getDefaultFormat());
  }

  /**
   * Convert today's date to String based on formatting
   *
   * @param format the formatting string based on http://bit.ly/1ODjMxk
   * @return the formatted date string
   */
  public static String getToday(String format) {
    return getDate(format, Calendar.getInstance());
  }

  /**
   * Convert the date to String
   *
   * @param calendar the Calendar variable to convert
   * @return the formatted date string
   */
  public static String getDate(Calendar calendar) {
    return getDate(getDefaultFormat(), calendar.getTime());
  }

  /**
   * Convert the date to String based on formatting
   *
   * @param format   the formatting string based on http://bit.ly/1ODjMxk
   * @param calendar the Calendar variable to convert
   * @return the formatted date string
   */
  public static String getDate(String format, Calendar calendar) {
    return getDate(format, calendar.getTime());
  }

  /**
   * Convert the date to String
   *
   * @param date the Date to be converted
   * @return the formatted date string
   */
  public static String getDate(Date date) {
    return getDate(getDefaultFormat(), date, Locale.getDefault());
  }

  /**
   * Convert the date to String based on formatting
   *
   * @param format the formatting string based on http://bit.ly/1ODjMxk
   * @param date   the Date to be converted
   * @return the formatted date string
   */
  public static String getDate(String format, Date date) {
    return getDate(format, date, Locale.getDefault());
  }

  /**
   * Convert the date to String and locale
   *
   * @param calendar the Calendar variable to convert
   * @param locale   the Locale to convert in
   * @return the formatted date string
   */
  public static String getDate(Calendar calendar, Locale locale) {
    return getDate(getDefaultFormat(), calendar.getTime(), locale);
  }

  /**
   * Convert the date to String based on formatting and locale
   *
   * @param format   the formatting string based on http://bit.ly/1ODjMxk
   * @param calendar the Calendar variable to convert
   * @param locale   the Locale to convert in
   * @return the formatted date string
   */
  public static String getDate(String format, Calendar calendar, Locale locale) {
    return getDate(format, calendar.getTime(), locale);
  }

  /**
   * Convert the date to String based on locale
   *
   * @param date   the Date to be converted
   * @param locale the Locale to convert in
   * @return the formatted date string
   */
  public static String getDate(Date date, Locale locale) {
    return getDate(getDefaultFormat(), date, locale);
  }

  /**
   * Convert the date to String based on formatting and locale
   *
   * @param format the formatting string based on http://bit.ly/1ODjMxk
   * @param date   the Date to be converted
   * @param locale the Locale to convert in
   * @return the formatted date string
   */
  public static String getDate(String format, Date date, Locale locale) {
    SimpleDateFormat timeFormatter = new SimpleDateFormat(format, locale);
    return timeFormatter.format(date);
  }

}
