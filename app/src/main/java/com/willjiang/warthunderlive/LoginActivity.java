package com.willjiang.warthunderlive;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Request;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.Network.RequestHelper;
import com.willjiang.warthunderlive.Network.RequestMaker;

/**
 * Created by Will on 1/21/16.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final View rootView = findViewById(R.id.login);
        final Context context = this;
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("type", "login");
                // the account below is created by myself, and not used for anything but testing
                args.putString("username", "byzanz@gmail.com");
                args.putString("password", "byzanz");
                new RequestMaker(context, rootView, args).execute(args);
            }
        });
    }

    public void setUserID(String userID) {
        SharedPreferences prefs = this.getSharedPreferences(
                this.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putString(API.userIDKey, userID).commit();
        API.userID = userID;
    }

    @Deprecated
    public void restoreUserID() {
        ((MainActivity) getParent()).restoreUserID();
    }
}
