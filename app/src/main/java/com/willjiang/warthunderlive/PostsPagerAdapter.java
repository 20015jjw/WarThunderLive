package com.willjiang.warthunderlive;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.UI.PostsFragment;

public class PostsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private View rootView;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private RequestMaker mRequestMaker;
    private String tabTitles[];
    private int period;

    public PostsPagerAdapter(View rootView, FragmentManager fragmentManager, Context context, int period) {
        super(fragmentManager);
        this.rootView = rootView;
        this.mContext = context;
        this.period = period;
        tabTitles = mContext.getResources().
            getStringArray(R.array.tab_headers);
    }

    @Override
    public int getCount() {
        return 8;
    }

    /* only called when a fragment for that position does not exist */
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return PostsFragment.newInstance(0, "All", period);
            case 1:
                return PostsFragment.newInstance(1, "Images", period);
            case 2:
                return PostsFragment.newInstance(2, "Videos", period);
            case 3:
                return PostsFragment.newInstance(3, "Quotes", period);
            case 4:
                return PostsFragment.newInstance(5, "Camos", period);
            case 5:
                return PostsFragment.newInstance(6, "Missions", period);
            case 6:
                return PostsFragment.newInstance(7, "Locations", period);
            case 7:
                return PostsFragment.newInstance(8, "Models", period);
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

    public void setPeriod (int period) {
        Log.v("setting Period", "" + period);
        this.period = period;
        for (int i = 0, size = registeredFragments.size(); i < size; i++) {
            ((PostsFragment) registeredFragments.valueAt(i)).setPeriod(period);
        }
    }

    public int getPeriod () {
        return this.period;
    }

    public void refresh() {
        setPeriod(getPeriod());
    }

}
