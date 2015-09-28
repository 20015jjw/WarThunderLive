package com.willjiang.warthunderlive.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.PostsAdapter;
import com.willjiang.warthunderlive.R;

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    private PostsAdapter mPostsAdapter;
    private ArrayList posts;
    private View rootView;

    private int curPage;
    private int lastPage;

    private int load;

    public static PostsFragment newInstance(int index, String catalog, int period) {
        // it generates new fragments whenever it returns to Mainactivity...
        Log.v("postsFrag", "new item");
        PostsFragment postsFragment = new PostsFragment();

        postsFragment.curPage = 0;
        postsFragment.lastPage = -1;
        postsFragment.load = 25;

        Bundle args = new Bundle();
        args.putString("type", "feed");
        args.putInt("index", index);
        args.putInt("page", 0);
        args.putString("catalog", catalog);
        args.putInt("period", period);
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

        RecyclerView postsList = (RecyclerView) rootView.findViewById(R.id.posts_list);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        postsList.setLayoutManager(layoutManager);
        postsList.setAdapter(mPostsAdapter);

        postsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                int totalItemCount = layoutManager.getChildCount();
                int firstVisibleItem= layoutManager.findFirstVisibleItemPositions(null)[0];

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
            RequestMaker requestMaker = new RequestMaker(getActivity(), rootView, args);
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

    public void setPeriod(int period) {
        Bundle args = this.getArguments();
        args.putInt("period", period);
        clearPosts();
        request();
    }

    private void clearPosts() {
        mPostsAdapter.clear();
        curPage = 0;
        lastPage = -1;
        load = 25;
    }

}
