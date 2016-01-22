package com.willjiang.warthunderlive.Network;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.willjiang.warthunderlive.LoginActivity;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.WTLApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestMaker extends AsyncTask<Bundle , Void, String> {
    private Context mContext;
    private View rootView;
    private List response_list;
    private String response_raw;
    private Bundle args;
    private String type;

    private OkHttpClient mClient;

    public RequestMaker(Context context, View rootView, Bundle args) {
        super();
        this.rootView = rootView;
        this.args = args;
        this.mContext = context;
        this.mClient = ((WTLApplication) context.getApplicationContext()).getClient();
        type = args.getString("type");
    }

    public InputStream makeRequest() throws IOException {
        Request request = RequestHelper.getRequest(args);
        Response response = mClient.newCall(request).execute();
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

    private String getUserID(String response_raw) throws IOException {
        Pattern pattern = Pattern.compile("'user_id':\\d+");
        Matcher matcher = pattern.matcher(response_raw);
        if (matcher.find()) {
            return matcher.group(0).substring(10);
        } else {
            throw new IOException("Login failed: unable to find userID");
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (type.equals("feed")) {
            RecyclerView posts_list = (RecyclerView) rootView.findViewById(R.id.posts_list);
            ((PostsAdapter) posts_list.getAdapter()).addAll(response_list);
        } else {
            try {
                ((LoginActivity) mContext).setUserID(getUserID(response_raw));
            } catch (IOException e) {
                Log.e("RequestMaker", "Unable to log in");
            }
            Log.v("userID", API.userID);
        }
    }

}



