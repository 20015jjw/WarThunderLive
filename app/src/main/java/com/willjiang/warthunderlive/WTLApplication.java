package com.willjiang.warthunderlive;

import android.app.Application;
import android.content.res.Configuration;

import com.squareup.picasso.Picasso;

public class WTLApplication extends Application{

    private Picasso mPicasso;

    public Picasso getPicasso() {
        return mPicasso;
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
//        refWatcher = LeakCanary.install(this);
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
