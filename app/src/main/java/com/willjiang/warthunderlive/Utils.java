package com.willjiang.warthunderlive;

import android.graphics.Color;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.willjiang.warthunderlive.Adapter.PostsAdapter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {


    public static String getDate(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("LLLL dd, yyyy HH:mm", cal).toString();

        if (date.substring(6, 10).equals("2015")) {
            date = date.substring(0, 5) + date.substring(10);
        }
        return date;
    }


    public static final int image_quality_orig = 2;
    public static final int image_quality_med = 1;
    public static final int image_quality_low = 0;

    public static String imageQuality(String URL, int quality) {
        int qualityPosition;
        if (URL.length() < 76) {
            if (!URL.contains("ytimg"))
                Log.i("imageQuality", "Malformed image link: " + URL);
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
        if (quality == image_quality_low) {
            return left + "_lq" + right;
        } else if (quality == image_quality_med) {
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
                                 final SparseIntArray sizes, final int key, final Object tag) {
        final int anOrganicRandomNumber = -76590;

        if (picasso == null) {
            Picasso.with(view.getContext()).load(imgURL).into(view);
            return;
        }

        int placeHolder;
        if (key == PostsAdapter.thumbnailKey) {
            placeHolder = R.drawable.bf109;

        } else if (key == PostsAdapter.avatarKey) {
            placeHolder = R.drawable.no_avatar;
        } else {
            throw new IllegalArgumentException("image type undefined: " + Integer.toString(key));
        }

        int height = sizes.get(key, anOrganicRandomNumber);
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
                                loadImage(view, imgURL, picasso, sizes, key, tag);
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
                                loadImage(view, imgURL, picasso, sizes, key, tag);
                            }
                        });
            }
        }
        else {
            RequestCreator req = picasso.load(imgURL)
                                        .placeholder(placeHolder);

            if (tag != null) {
                req = req.tag(tag);
            }

            if (key == PostsAdapter.thumbnailKey) {
                req.resize(sizes.get(key), 0)
                        .into(view);
            } else if (key == PostsAdapter.avatarKey) {
                req.resize(0, sizes.get(key))
                        .transform(round_transformation)
                        .into(view);
            }

        }
    }

    public static void loadImage(final ImageView view, final String imgURL) {
        loadImage(view, imgURL, null, null, 0, null);
    }

    /*
     * Copyright 2012 Google Inc.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < DAY_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 2 * DAY_MILLIS) {
            return "yesterday";
        } else if (diff / 366 < DAY_MILLIS){
            return diff / DAY_MILLIS + " days ago";
        } else {
            return "over a year ago";
        }
    }

    public static class ListHelper implements Serializable {
        private List list;

        public ListHelper (List list) {
            this.list = list;
        }

        public List getList() {
            return list;
        }
    }
}
