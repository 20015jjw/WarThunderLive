package com.willjiang.warthunderlive.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.StaggeredGridView;
import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.PostsAdapter;
import com.willjiang.warthunderlive.R;

import java.util.ArrayList;

/**
 * Created by Will on 9/3/15.
 */
public class PostsFragment extends Fragment {

    private ArrayAdapter mPostsAdapter;
    private ArrayList posts;
    private View rootView;

    private int curPage;
    private int lastPage;

    private int load;

    public static PostsFragment newInstance(int index, String catalog) {
        PostsFragment postsFragment = new PostsFragment();

        postsFragment.curPage = 0;
        postsFragment.lastPage = -1;
        postsFragment.load = 25;

        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putInt("page", 0);
        args.putString("catalog", catalog);
        postsFragment.setArguments(args);
        return postsFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posts = new ArrayList();
        mPostsAdapter = new PostsAdapter(getActivity(), posts);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        StaggeredGridView postsList = (StaggeredGridView) rootView.findViewById(R.id.posts_list);
        postsList.setAdapter(mPostsAdapter);

        postsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.v("posts_scroll_first", String.valueOf(firstVisibleItem));
                Log.v("posts_scroll_total", String.valueOf(totalItemCount));
                Log.v("posts_scroll_cur_page", String.valueOf(curPage));
                if (firstVisibleItem >= load - 1) {
                    load += 25;
                    increasePage(totalItemCount);
                }
            }
        });

        request();
        return rootView;
    }

    private void request() {
        if (lastPage < curPage) {
            Bundle args = getArguments();
            args.putInt("page", curPage);
            Log.v("curPage", String.valueOf(curPage));
            RequestMaker requestMaker = new RequestMaker(rootView, getArguments());
            requestMaker.execute(this.getArguments());
            lastPage ++;
        }
    }

    public void increasePage(int curLoad) {
        Log.v("increase", "" + curLoad);
        if (curLoad < load) {
            curPage ++;
            request();
        }


    }

}
