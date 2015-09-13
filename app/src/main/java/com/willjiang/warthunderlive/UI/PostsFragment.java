package com.willjiang.warthunderlive.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private int index;
    private String catelog;

    private ArrayAdapter mPostsAdapter;
    private ArrayList posts;
    private View rootView;

    private int curPage = 0;
    private int lastPage = -1;

    public static PostsFragment newInstance(int index, String catalog) {
        PostsFragment postsFragment = new PostsFragment();
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

        request();
        return rootView;
    }

    public void request() {
        if (lastPage < curPage) {
            RequestMaker requestMaker = new RequestMaker(rootView, getArguments());
            requestMaker.execute(this.getArguments());
            lastPage ++;
        }
    }

}
