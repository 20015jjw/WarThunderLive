package com.willjiang.warthunderlive.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.willjiang.warthunderlive.Adapter.PostsAdapter;
import com.willjiang.warthunderlive.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RequestMaker extends AsyncTask<Bundle , Void, String> {
    private Context mContext;
    private View rootView;
    private List response_list;
    private String response_raw;
    private Bundle args;
    private String type;


    private OkHttpClient CLIENT = new OkHttpClient();

    public RequestMaker () {
        super();
    }

    public RequestMaker(Context context, View rootView, Bundle args) {
        super();
        this.rootView = rootView;
        this.args = args;
        this.mContext = context;
        type = args.getString("type");
    }

    public InputStream makeRequest() throws IOException {
        Request request = RequestHelper.getRequest(args);
        Response response = CLIENT.newCall(request).execute();
        return response.body().byteStream();
    }

    @Override
    protected String doInBackground(Bundle... params) {
        try {

            JsonParser parser = new JsonParser(this.mContext);
            if (type.equals("feed")) {
                response_list = parser.readJsonStream(makeRequest());
            } else {
                BufferedReader r = new BufferedReader(new InputStreamReader(makeRequest()));
                StringBuilder t = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    t.append(line);
                }
                response_raw = t.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (type.equals("feed")) {
            RecyclerView posts_list = (RecyclerView) rootView.findViewById(R.id.posts_list);
            ((PostsAdapter) posts_list.getAdapter()).addAll(response_list);
        } else {
            WebView webView = (WebView) rootView.findViewById(R.id.login_webview);
            webView.loadData(response_raw, "text/html", "UTF-8");
        }
    }

}



