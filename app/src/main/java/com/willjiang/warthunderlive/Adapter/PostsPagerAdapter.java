package com.willjiang.warthunderlive.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.UI.PostsFragment;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private ViewPager mViewPager;
    private View rootView;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private String tabTitles[];
    private int period;

    public PostsPagerAdapter(View rootView, FragmentManager fragmentManager,
                             Context context, int period, ViewPager viewPager) {
        super(fragmentManager);
        this.rootView = rootView;
        this.mContext = context;
        this.period = period;
        this.mViewPager = viewPager;
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
                return PostsFragment.newInstance(mViewPager, index, "All", period, index);
            case 1:
                return PostsFragment.newInstance(mViewPager, index, "Images", period, index);
            case 2:
                return PostsFragment.newInstance(mViewPager, index, "Videos", period, index);
            case 3:
                return PostsFragment.newInstance(mViewPager, index, "Quotes", period, index);
            case 4:
                return PostsFragment.newInstance(mViewPager, index, "Camos", period, index);
            case 5:
                return PostsFragment.newInstance(mViewPager, index, "Missions", period, index);
            case 6:
                return PostsFragment.newInstance(mViewPager, index, "Locations", period, index);
            case 7:
                return PostsFragment.newInstance(mViewPager, index, "Models", period, index);
            default:
                return null;
        }
    }

    // http://stackoverflow.com/questions/10396321/remove-fragment-page-from-viewpager-in-android
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
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
        super.destroyItem(container, position, object);
        registeredFragments.get(position).onDestroy();
    }

    public void setPeriod (int period) {
        this.period = period;
        for (int i = 0, size = registeredFragments.size(); i < size; i++) {
            ((PostsFragment) registeredFragments.valueAt(i)).setPeriod(period);
        }
    }

    public int getPeriod () {
        return this.period;
    }

    // refresh all the pages
    public void refresh() {
        setPeriod(getPeriod());
    }

    // refresh only one view when the content should change
    public void refreshView(int position) {
        ((PostsFragment) registeredFragments.get(position)).refresh();
    }

    public void pauseAll() {
        for (int i = 0, size = registeredFragments.size(); i < size; i++) {
            ((PostsFragment) registeredFragments.valueAt(i)).onPause();
        }
    }

}