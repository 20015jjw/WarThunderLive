package com.willjiang.warthunderlive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
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

    private View rootView;
    private RequestMaker mRequestMaker;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public PostsPagerAdapter(View rootView, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.rootView = rootView;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return PostsFragment.newInstance(mRequestMaker, 0, "All");
            case 1:
                return PostsFragment.newInstance(mRequestMaker, 1, "Camo");
            case 2:
                return PostsFragment.newInstance(mRequestMaker, 5, "Mission");
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
        return "Page " + index;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public CharSequence getPageTitle(String catalog) {
        return catalog;
    }
}
