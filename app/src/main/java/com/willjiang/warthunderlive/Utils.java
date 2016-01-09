package com.willjiang.warthunderlive;

import android.graphics.Color;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

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
        if (URL.length() < 76) {
            return URL;
        }
        if (URL.startsWith("http")) {
            qualityPosition = 80;
        } else {
            URL = "http://live.warthunder.com" + URL;
            qualityPosition = 76;
        }

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

    public static String toSummary(Spanned str, int minLength) {
        String copy = str.toString();
        String summary = str.toString();
        if (copy.length() > minLength + 10) {
            int firstSpace = copy.indexOf(" ", minLength);
            if (firstSpace != -1) {
                summary = copy.substring(0, firstSpace) + "...";
            }
        }
        return summary.trim();
    }

    public static final Transformation round_transformation =
            new RoundedTransformationBuilder()
            .borderColor(Color.TRANSPARENT)
            .cornerRadiusDp(30)
            .oval(true)
            .build();

    public static void loadImage(final ImageView view, final String imgURL, final Picasso picasso,
                                 final SparseIntArray sizes, final int key) {

        final int anOrganicRandomNumber = -76590;

        int height = sizes.get(key, anOrganicRandomNumber);

        int placeHolder = 0;
        if (key == PostsAdapter.thumbnailKey) {
            placeHolder = R.drawable.bf109;

        } else if (key == PostsAdapter.avatarKey) {
            placeHolder = R.drawable.no_avatar;
        }

        if (height == anOrganicRandomNumber) {
            if (key == PostsAdapter.thumbnailKey) {
                view.getViewTreeObserver()
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            // Wait until layout to call Picasso
                            @Override
                            public void onGlobalLayout() {
                                view.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                                sizes.put(key, view.getWidth());
                                loadImage(view, imgURL, picasso, sizes, key);
                            }
                        });
            } else if (key == PostsAdapter.avatarKey) {
                 view.getViewTreeObserver()
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            // Wait until layout to call Picasso
                            @Override
                            public void onGlobalLayout() {
                                view.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                                sizes.put(key, view.getHeight());
                                loadImage(view, imgURL, picasso, sizes, key);
                            }
                        });
            }
        }
        else {
            RequestCreator req = picasso.load(imgURL)
                                        .placeholder(placeHolder);
            if (key == PostsAdapter.thumbnailKey) {
                req.resize(sizes.get(key), 0)
//                        .memoryPolicy(MemoryPolicy.NO_CACHE)
//                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(view);
            } else if (key == PostsAdapter.avatarKey) {
                req.resize(0, sizes.get(key))
//                        .memoryPolicy(MemoryPolicy.NO_CACHE)
//                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .transform(round_transformation)
                        .into(view);
            }

        }
    }
}
