package com.example.fbooking.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatUtils {
    public static <T> String format(T price, String unit, String dorc) {
        return format(price, unit, isDecrease(dorc));
    }

    public static <T> String format(T price, String unit, boolean isDecrease) {
        String plus = isDecrease ? "-" : "";
        return plus + getFormatPrice(price) + " " + unit;
    }

    public static <T> String format(T price, String unit) {
        return getFormatPrice(price) + " " + unit;
    }

    public static <T> String format(T price) {
        return getFormatPrice(price);
    }

    public static double revert(String s) {
        try {
            return Double.parseDouble(s.trim().replace(",", ""));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }


    public static boolean isDecrease(String dorc) {
        return dorc != null && dorc.equalsIgnoreCase("D");
    }

    private static <T> String getFormatPrice(T price) {
        double castToPrice = 0;
        try {
            castToPrice = Double.parseDouble(price + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return NumberFormat.getNumberInstance(Locale.US).format(castToPrice);
    }
}
