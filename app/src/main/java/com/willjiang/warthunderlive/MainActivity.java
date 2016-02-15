package com.willjiang.warthunderlive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.willjiang.warthunderlive.Adapter.PostsPagerAdapter;
import com.willjiang.warthunderlive.Network.API;

public class MainActivity extends AppCompatActivity {

    public static MainActivity activity;
    private ViewPager mPostsPager;
    private PostsPagerAdapter mPostsPagerAdapter;

    private final String mCurrentPageKey = "currentPage";
    private final String mCurrentPeriodKey = "currentPeriod";

    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        restoreUserID();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPostsPager = (ViewPager) findViewById(R.id.posts_pager);
        mPostsPager.setOffscreenPageLimit(1);
        mPostsPagerAdapter = new PostsPagerAdapter(this.findViewById(R.id.main),
                getSupportFragmentManager(), this, 0, mPostsPager);
        mPostsPager.setAdapter(mPostsPagerAdapter);

        mPostsPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPostsPagerAdapter.refreshView(position);
            }
        });

        TabLayout PostsHeader = (TabLayout) findViewById(R.id.posts_pager_header);
        PostsHeader.setupWithViewPager(mPostsPager);

        drawer = setupDrawer(toolbar);
    }

    private Drawer setupDrawer(Toolbar toolbar) {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.profile_image)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
//                .addProfiles(
//                        new ProfileDrawerItem()
//                                .withName("__StrafeMike__")
//                                .withIcon("http://cdn-live.warthunder.com/uploads/49/55a47dbf18e99222ecbcf60aae8da2b9b9e3e3/avatar.png")
//                )
                .build();
        PrimaryDrawerItem feed = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_feed));
        SecondaryDrawerItem notification = new SecondaryDrawerItem()
                .withName(getString(R.string.drawer_notification));
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(header)
                .addDrawerItems(feed)
                .addDrawerItems(notification)
                .build();
        return drawer;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPostsPager.setCurrentItem(savedInstanceState.getInt(mCurrentPageKey));
        mPostsPagerAdapter.setPeriod(savedInstanceState.getInt(mCurrentPeriodKey));

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState = drawer.saveInstanceState(savedInstanceState);
        savedInstanceState.putInt(mCurrentPageKey, mPostsPager.getCurrentItem());
        savedInstanceState.putInt(mCurrentPeriodKey, mPostsPagerAdapter.getPeriod());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        updatePeriodIcon(menu.findItem(R.id.period_switch));
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem login = menu.findItem(R.id.login_activity);
        if (API.isLogin()) {
            login.setTitle("Logout");
            login.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else {
            login.setTitle("L");
            login.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.period_switch:
                onSwitch(item);
                return true;
            case R.id.refresh:
                onRefresh();
                return true;
            case R.id.login_activity:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onSwitch(MenuItem item) {
        int curPeriod = mPostsPagerAdapter.getPeriod();
        if (curPeriod == 7) {
            curPeriod = 30;
        } else if (curPeriod == 30) {
            curPeriod = 0;
        } else if (curPeriod == 0) {
            curPeriod = 7;
        }
        mPostsPagerAdapter.setPeriod(curPeriod);
        updatePeriodIcon(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void updatePeriodIcon(MenuItem periodItem) {
        int curPeriod = mPostsPagerAdapter.getPeriod();
        if (curPeriod == 7 || curPeriod == 30) {
            periodItem.setTitle(Integer.toString(curPeriod));
        } else {
            periodItem.setTitle("∞");
        }
    }

    public void onRefresh() {
        mPostsPagerAdapter.refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("main", "paused");
        mPostsPagerAdapter.pauseAll();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void restoreUserID() {
        SharedPreferences prefs = this.getSharedPreferences(
                this.getPackageName(), Context.MODE_PRIVATE);
        String savedUserID = prefs.getString(API.userIDKey, "0");
        if (!savedUserID.equals("0")) {
            API.userID = savedUserID;
        }
    }

}
