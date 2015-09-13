package com.willjiang.warthunderlive;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.UI.PostsFragment;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Will on 9/7/15.
 */
public class PostsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private View rootView;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private RequestMaker mRequestMaker;
    private String tabTitles[];

    public PostsPagerAdapter(View rootView, FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.rootView = rootView;
        this.mContext = context;
        tabTitles = mContext.getResources().
            getStringArray(R.array.tab_headers);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Fragment getItem(int index) {
        Log.v("pager adapter", "getting item" + index);
        switch (index) {
            case 0:
                return PostsFragment.newInstance(0, "All");
            case 1:
                return PostsFragment.newInstance(1, "Images");
            case 2:
                return PostsFragment.newInstance(2, "Videos");
            case 3:
                return PostsFragment.newInstance(3, "Quotes");
            case 4:
                return PostsFragment.newInstance(5, "Camos");
            case 5:
                return PostsFragment.newInstance(6, "Missions");
            case 6:
                return PostsFragment.newInstance(7, "Locations");
            case 7:
                return PostsFragment.newInstance(8, "Models");
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int index) {
        return tabTitles[index];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
