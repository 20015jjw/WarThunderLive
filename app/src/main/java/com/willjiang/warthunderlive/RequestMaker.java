package com.willjiang.warthunderlive;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Will on 9/1/15.
 */
public class RequestMaker {
    private final static String baseURL = "http://live.warthunder.com/api/feed";
    private final static String unLogged= baseURL + "/get_unlogged";

    private OkHttpClient CLIENT = new OkHttpClient();

    public InputStream makeRequest() throws IOException, JSONException {

        Request request = new Request.Builder()
                .url(unLogged)
                .build();

        Response response = CLIENT.newCall(request).execute();
        Log.v("Request", response.body().string());
        Log.v("Request", response.body().string());

        return response.body().byteStream();
    }

}

