package com.willjiang.warthunderlive.Network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.willjiang.warthunderlive.PostsAdapter;
import com.willjiang.warthunderlive.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Will on 9/1/15.
 */
public class RequestMaker extends AsyncTask<String, Void, String> {
    private Context mContext;
    private View rootView;
    private List response;

    private final static String baseURL = "http://live.warthunder.com/api/feed";
    private final static String unLogged = baseURL + "/get_unlogged";

    private OkHttpClient CLIENT = new OkHttpClient();

    public InputStream makeRequest() throws IOException {

        Request request = new Request.Builder()
                .url(unLogged)
                .build();

        Response response = CLIENT.newCall(request).execute();

        return response.body().byteStream();
    }

    public RequestMaker(Context context, View rootView) {
        this.mContext = context;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.v("req", "started");
        try {
            JsonParser parser = new JsonParser(this.mContext);
            response = parser.readJsonStream(makeRequest());
            Log.v("req", "finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.v("update", "started");
        ListView listView = (ListView) rootView.findViewById(R.id.posts_list);
        ((PostsAdapter) listView.getAdapter()).addAll(response);
    }

}



