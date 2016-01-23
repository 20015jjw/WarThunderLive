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
    private CookieManager mCookieManager;
    private PersistentCookieStore mCookieStore;

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
        mCookieStore = new PersistentCookieStore(getApplicationContext());
        mCookieManager = new CookieManager(mCookieStore, CookiePolicy.ACCEPT_ALL);
        mClient.setCookieHandler(mCookieManager);
    }

    public void clearCookie() {
        mCookieStore.removeAll();
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
