package com.willjiang.warthunderlive;

import android.app.Application;
import android.content.res.Configuration;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;
import com.willjiang.warthunderlive.Network.PersistentCookieStore;

import java.net.CookieManager;
import java.net.CookiePolicy;

public class WTLApplication extends Application{

    private Picasso mPicasso;
    private OkHttpClient mClient;

    public Picasso getPicasso() {
        return mPicasso;
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPicasso = new Picasso.Builder(this).build();
        Picasso.setSingletonInstance(mPicasso);

        mClient = new OkHttpClient();
        mClient.setCookieHandler(new CookieManager(
                new PersistentCookieStore(getApplicationContext()),
                CookiePolicy.ACCEPT_ALL));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
