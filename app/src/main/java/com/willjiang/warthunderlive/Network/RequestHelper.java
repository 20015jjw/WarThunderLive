package com.willjiang.warthunderlive.Network;

import android.os.Bundle;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created by Will on 1/20/16.
 */
public class RequestHelper {

    public static Request getRequest(Bundle args) {
        String type = args.getString("type");
        Request request = null;

        if (type.equals("feed")) {
            return getFeedRequest(args);
        } else if (type.equals("login")) {
            return getLoginRequest(args);
        } else {
            throw new IllegalArgumentException("Illegal request");
        }
    }

    private static Request getFeedRequest(Bundle args) {
        String content = String.valueOf(args.getInt("content"));
        String page = String.valueOf(args.getInt("page"));
        String period = String.valueOf(args.getInt("period"));

        RequestBody postBody = new FormEncodingBuilder()
                .add("page", page)
                .add("content", content)
                .add("sort", "1")
                .add("user", API.userID)
                .add("period", period)
                .build();


        Request request = new Request.Builder()
                .url(API.userID.equals("0") ? API.unLoggedFeed : API.subscribedUserFeed)
                .post(postBody)
                .build();

        return request;
    }

    private static Request getLoginRequest(Bundle args) {
        String username = String.valueOf(args.getString("username"));
        String password = String.valueOf(args.getString("password"));

        RequestBody postBody = new FormEncodingBuilder()
                .add("login", username)
                .add("password", password)
                .add("action", "")
                .add("service", "")
                .add("zendesk_locale_id", "")
                .add("referer", "")
                .add("refresh_token", "1")
                .build();

        Request request = new Request.Builder()
                .url(API.loginURL)
                .post(postBody)
                .build();

        return request;
    }

}
