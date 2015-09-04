package com.willjiang.warthunderlive;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Will on 9/3/15.
 */
public class PostsFragement extends Fragment {

    private ArrayAdapter mPostsAdapter;
    private ArrayList posts;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        posts = new ArrayList();

        mPostsAdapter = new PostsAdapter(getActivity(), posts);
        ListView postsList = (ListView) rootView.findViewById(R.id.posts_list);
        postsList.setAdapter(mPostsAdapter);
        return rootView;
    }

}
