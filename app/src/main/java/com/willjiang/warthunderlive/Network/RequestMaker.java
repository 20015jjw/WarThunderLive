package com.willjiang.warthunderlive.Network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.etsy.android.grid.StaggeredGridView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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

    private OkHttpClient CLIENT = new OkHttpClient();

    public InputStream makeRequest() throws IOException {

        RequestBody postBody = new FormEncodingBuilder()
                .add("page", "0")
                .add("content", "0")
                .add("sort", "1")
                .add("user", "0")
                .add("period", "30")
                .build();

        Request request = new Request.Builder()
                .url(API.unLogged)
                .post(postBody)
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
        StaggeredGridView listView = (StaggeredGridView) rootView.findViewById(R.id.posts_list);
        ((PostsAdapter) listView.getAdapter()).addAll(response);
    }

}



