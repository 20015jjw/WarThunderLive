package com.willjiang.warthunderlive;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.willjiang.warthunderlive.Adapter.PostsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPostsPager;
    private PostsPagerAdapter mPostsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        Log.v("main", "restoring");
        mPostsPager.setCurrentItem(savedInstanceState.getInt("currentPage"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        /* only called when a fragment for that position does not exist */
        savedInstanceState.putInt("currentPage", mPostsPager.getCurrentItem());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.period_switch) {
            onSwitch(item);
        } else if (id == R.id.refresh) {
            onRefresh();
        } else if (id == R.id.login_activity) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSwitch(MenuItem item) {
        int curPeriod = mPostsPagerAdapter.getPeriod();
        if (curPeriod == 7) {
            curPeriod = 30;
            item.setTitle(Integer.toString(curPeriod));
        } else if (curPeriod == 30) {
            curPeriod = 0;
            item.setTitle("∞");
        } else if (curPeriod == 0) {
            curPeriod = 7;
            item.setTitle(Integer.toString(curPeriod));
        }
        mPostsPagerAdapter.setPeriod(curPeriod);
    }

    private void onRefresh() {
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
        Log.v("main", "stopped");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("main", "destroyed");
    }

}
