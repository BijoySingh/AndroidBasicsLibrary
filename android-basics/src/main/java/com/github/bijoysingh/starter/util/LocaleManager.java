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

    public static String toString(Double arg) {
        return toString("%f", arg);
    }

    public static String toString(Integer arg) {
        return toString("%d", arg);
    }

    public static String toString(Float arg) {
        return toString("%f", arg);
    }

    public static String toString(Boolean arg) {
        return toString("%b", arg);
    }

    public static String toString(Character arg) {
        return toString("%c", arg);
    }
}
