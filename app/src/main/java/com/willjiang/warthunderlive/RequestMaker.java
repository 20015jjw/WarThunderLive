package com.willjiang.warthunderlive;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Will on 9/1/15.
 */
public class RequestMaker {
    private final static String baseURL = "http://live.warthunder.com/api/feed";
    private final static String unLogged= baseURL + "/get_unlogged";

    private OkHttpClient CLIENT = new OkHttpClient();

    public String makeRequest() throws IOException {

        Request request = new Request.Builder()
                .url(baseURL)
                .build();

        Response response = CLIENT.newCall(request).execute();
        Log.v("Request", response.body().string());
        response.body().byteStream();
    }

}

