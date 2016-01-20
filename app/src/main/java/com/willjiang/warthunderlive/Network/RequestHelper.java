package com.willjiang.warthunderlive.Network;

import android.os.Bundle;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by Will on 1/20/16.
 */
public class RequestHelper {

    public static Request getRequest(Bundle args) {

        String type = args.getString("type");
        Request request;

        if (type.equals("feed")) {
            String content = String.valueOf(args.getInt("content"));
            String page = String.valueOf(args.getInt("page"));
            String period = String.valueOf(args.getInt("period"));

            RequestBody postBody = new FormEncodingBuilder()
                    .add("page", page)
                    .add("content", content)
                    .add("sort", "1")
                    .add("user", "0")
                    .add("period", period)
                    .build();

            request = new Request.Builder()
                    .url(API.unLoggedFeed)
                    .post(postBody)
                    .build();

            return request;
        } else {
            throw new IllegalArgumentException("Illegal request");
        }

    }

}
