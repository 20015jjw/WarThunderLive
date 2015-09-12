package com.willjiang.warthunderlive;

import android.app.ActionBar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.UI.PostsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager postsPager = (ViewPager) findViewById(R.id.posts_pager);
        PostsPagerAdapter postsPagerAdapter = new PostsPagerAdapter(this.findViewById(R.id.main),
                getSupportFragmentManager(), this);
        postsPager.setAdapter(postsPagerAdapter);
        TabLayout PostsHeader = (TabLayout) findViewById(R.id.posts_pager_header);
        PostsHeader.setupWithViewPager(postsPager);

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
        if (id == R.id.action_settings) {
        }

        return super.onOptionsItemSelected(item);
    }
}
