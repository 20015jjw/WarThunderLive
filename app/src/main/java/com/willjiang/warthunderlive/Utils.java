package com.willjiang.warthunderlive;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("LLLL dd, yyyy HH:mm", cal).toString();

        if (date.substring(6, 10).equals("2015")) {
            date = date.substring(0, 5) + date.substring(10);
        }
        return date;
    }


    // quality : 0 -> low; 1 -> medium; 2 -> original
    public static String imageQuality(String URL, int quality) {
        int qualityPosition = 80;
        String left = URL.substring(0, qualityPosition);
        String right = URL.substring(qualityPosition);
        if (right.startsWith("_mq") || right.startsWith("_lq")) {
            right = right.substring(3);
        }
        if (quality == 0) {
            return left + "_lq" + right;
        } else if (quality == 1) {
            return left + "_mq" + right;
        } else {
            return left + right;
        }
    }

    public static String toSummary(String str, int minLength) {
        String summary;
        if (str.length() > minLength + 10) {
            int firstSpace = str.indexOf(" ", minLength);
            summary = str.substring(0, firstSpace) + "...";
        } else {
            summary = str;
        }
        return summary;
    }
}
