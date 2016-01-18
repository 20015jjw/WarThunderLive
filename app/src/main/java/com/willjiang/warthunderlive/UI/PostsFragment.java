package com.willjiang.warthunderlive.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.RequestMaker;
import com.willjiang.warthunderlive.Adapter.PostsAdapter;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.Utils;
import com.willjiang.warthunderlive.WTLApplication;

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    public ViewPager vp;
    private PostsAdapter mPostsAdapter;
    private RecyclerView mPostsList;
    private StaggeredGridLayoutManager mLayoutManager;
    private ArrayList posts;
    private View rootView;

    private int curPage;
    private int lastPage;
    private int load;

    private int tag;

    private boolean refreshPending;

    public static PostsFragment newInstance(ViewPager vp, int index, String catalog, int period, int tag) {
        PostsFragment postsFragment = new PostsFragment();
        postsFragment.vp = vp;

        Bundle args = new Bundle();
        args.putString("type", "feed");
        args.putInt("index", index);
        args.putInt("content", index >= 4 ? index + 1 : index);
        args.putInt("page", 0);
        args.putString("catalog", catalog);
        args.putInt("period", period);
        postsFragment.setArguments(args);
        postsFragment.setTag(tag);;;

        return postsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.curPage = savedInstanceState.getInt("curPage");
            this.lastPage = savedInstanceState.getInt("lastPage");
            this.load = savedInstanceState.getInt("load");
            this.refreshPending = savedInstanceState.getBoolean("refreshPending");

            Bundle posts_bundle = savedInstanceState.getBundle("posts");
            Utils.ListHelper posts_list = (Utils.ListHelper) posts_bundle.getSerializable("posts");
            this.posts = (ArrayList) posts_list.getList();

            Log.v("posts frag", "restored");
            mPostsAdapter = new PostsAdapter((WTLApplication) getActivity().getApplication(), posts, tag);
        } else {
            posts = new ArrayList();
            this.curPage = 0;
            this.lastPage = -1;
            this.load = 25;
            this.refreshPending = true;
            mPostsAdapter = new PostsAdapter((WTLApplication) getActivity().getApplication(), posts, tag);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        mPostsList = (RecyclerView) rootView.findViewById(R.id.posts_list);
        mLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mPostsList.setLayoutManager(mLayoutManager);
        mPostsList.setAdapter(mPostsAdapter);

        mPostsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                int totalItemCount = mLayoutManager.getChildCount();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPositions(null)[0];

                if (firstVisibleItem >= load - 8) {
                    load += 25;
                    increasePage(totalItemCount);
                }
            }

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView,
                                             final int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPostsAdapter.resumeAll();
                } else {
                    mPostsAdapter.pauseAll();
                }
            }
        });

        request();
        return rootView;
    }

    public void request() {
        if (lastPage < curPage) {
            Bundle args = getArguments();
            args.putInt("page", curPage);
            RequestMaker requestMaker = new RequestMaker(getActivity(), rootView, args);
            requestMaker.execute(this.getArguments());
            lastPage ++;
        }
    }

    public void setTag (int tag) {
        this.tag = tag;
    }

    public void increasePage(int curLoad) {
        if (curLoad < load) {
            curPage ++;
            request();
        }
    }

    public void setPeriod(int period) {
        Bundle args = this.getArguments();
        args.putInt("period", period);
        clearPosts();
        if (this.vp.getCurrentItem() == this.getArguments().getInt("index", 100)) {
            request();
        } else {
            this.refreshPending = true;
        }
    }

    // refresh the content when necessary
    public void refresh() {
        if (refreshPending) {
            setPeriod(getArguments().getInt("period", 0));
            refreshPending = false;
        }
    }

    private void clearPosts() {
        mPostsAdapter.clear();
        curPage = 0;
        lastPage = -1;
        load = 25;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("curPage", curPage);
        savedInstanceState.putInt("lastPage", lastPage);
        savedInstanceState.putInt("load", load);
        savedInstanceState.putBoolean("refreshPending", refreshPending);

        Bundle bundle = new Bundle();
        bundle.putSerializable("posts", new Utils.ListHelper(posts));
        savedInstanceState.putBundle("posts", bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPostsAdapter.stopAll();
//        RefWatcher r = WTLApplication.getRefWatcher(getActivity());
//        r.watch(this);
//        r.watch(mPostsAdapter);
    }

}
