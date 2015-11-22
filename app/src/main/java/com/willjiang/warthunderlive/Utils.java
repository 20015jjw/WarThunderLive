package com.willjiang.warthunderlive;

import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        int qualityPosition;
        if (URL.startsWith("http")) {
            qualityPosition = 80;
        } else {
            URL = "http://live.warthunder.com" + URL;
            qualityPosition = 76;
        }

//        if (URL.length() < qualityPosition) {
//                Log.e("Utils.imageQuality", "URL too short: " + URL);
//        } else {
//            Log.v("Utils.imageQuality", "URL good: " + URL);
//        }

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
        String summary = str;
        if (str.length() > minLength + 10) {
            int firstSpace = str.indexOf(" ", minLength);
            if (firstSpace != -1) {
                summary = str.substring(0, firstSpace) + "...";
            }
        }
        return summary.trim();
    }

    public static void loadImage(final ImageView view, final String imgURL, final Picasso picasso,
                                 final SparseIntArray sizes, final int key) {

        int result = sizes.get(key, -10);
        if (result == -10) {
            view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    // Wait until layout to call Picasso
                    @Override
                    public void onGlobalLayout() {
                        // Ensure we call this only once
                        view.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        sizes.put(key, view.getWidth());

                        picasso
                            .load(imgURL)
                            .resize(view.getWidth(), 0)
                            .into(view);
                    }
                });
        }
        else {
            picasso
                .load(imgURL)
                .resize(sizes.get(key), 0)
                .into(view);
        }
    }
}
