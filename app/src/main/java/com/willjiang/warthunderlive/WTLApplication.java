package com.willjiang.warthunderlive;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
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

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                mPicasso.with(imageView.getContext())
                        .load(uri)
                        .placeholder(R.drawable.no_avatar)
                        .into(imageView);
            }

            @Override
        public void cancel(ImageView imageView) {
                mPicasso.cancelRequest(imageView);
            }
        });
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
