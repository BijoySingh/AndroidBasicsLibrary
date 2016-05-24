package com.github.bijoysingh.starter.util;

import java.util.Locale;

/**
 * Basic locale handling and simple functions
 * Created by bijoy on 1/31/16.
 */
public class LocaleManager {

    private static String toString(String format, Object object) {
        return String.format(Locale.getDefault(), format, object);
    }

    /**
     * Convert double to string
     *
     * @param arg the double value
     * @return the string converted value
     */
    public static String toString(Double arg) {
        return toString("%f", arg);
    }

    /**
     * Convert double to string with fixed precision
     *
     * @param arg the double value
     * @param precision the precision
     * @return the string converted value
     */
    public static String toString(Double arg, int precision) {
        return toString("%." + precision + "f", arg);
    }

    /**
     * Convert integer to string
     *
     * @param arg the integer value
     * @return the string converted value
     */
    public static String toString(Integer arg) {
        return toString("%d", arg);
    }

    /**
     * Convert float to string
     *
     * @param arg the float value
     * @return the string converted value
     */
    public static String toString(Float arg) {
        return toString("%f", arg);
    }


    /**
     * Convert float to string with fixed precision
     *
     * @param arg the float value
     * @param precision the precision
     * @return the string converted value
     */
    public static String toString(Float arg, int precision) {
        return toString("%." + precision + "f", arg);
    }

    /**
     * Convert boolean to string
     *
     * @param arg the boolean value
     * @return the string converted value
     */
    public static String toString(Boolean arg) {
        return toString("%b", arg);
    }

    /**
     * Convert character to string
     *
     * @param arg the character value
     * @return the string converted value
     */
    public static String toString(Character arg) {
        return toString("%c", arg);
    }
}
