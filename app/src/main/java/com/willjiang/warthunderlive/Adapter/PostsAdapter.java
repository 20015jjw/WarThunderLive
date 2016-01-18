package com.willjiang.warthunderlive.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.UI.PostCardHolder;
import com.willjiang.warthunderlive.WTLApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List posts;
    private Picasso mPicasso;
    protected int tag;
    private List<PostCardHolder> holders;

    public static final int thumbnailKey = 1;
    public static final int avatarKey = 2;

    public PostsAdapter(WTLApplication application, List posts, int tag) {
        this.mContext = application;
        this.posts = posts;
        this.mPicasso = application.getPicasso();
        this.tag = tag;
        this.holders = new LinkedList<PostCardHolder>();
    }

    public void addAll(List list) {
        if (list != null) {
            this.posts.addAll(list);
            notifyDataSetChanged();
        } else {
            Log.e("posts adapter", "empty post list");
        }
    }

    public void clear() {
        this.posts.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        HashMap post = (HashMap) posts.get(i);

        PostCardHolder card = (PostCardHolder) viewHolder;
        // id and language
        String id = (String) post.get(API.id);
        String lang = (String) post.get(API.language);
        card.setID(id);
        card.setLanguage(lang);

        // description
        String rawText = (String) post.get(API.description);
        Spanned parsedText = Html.fromHtml(rawText);
        card.setDescription(parsedText);

        // thumbnail
        ArrayList images = (ArrayList) post.get(API.images);
        String video_image_src = (String) post.get(API.video_info);
        if (!images.isEmpty()) {
            String imageURL = (String) (images).get(0);
            card.setThumbnailURL(imageURL);
            card.setImages(images);
        }
        if (video_image_src != null) {
            card.setThumbnailURL(video_image_src);
            card.setIs_video(true);
        }

        // author
        HashMap<String, String> author = (HashMap<String, String>) post.get(API.author);
        card.setAuthor(author);

        // timestamp
        String rawTimestamp = (String) post.get(API.timestamp);
        card.setTimestamp(rawTimestamp);

        card.setupInnerViewElements();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View postCard = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_postcard, null);
        PostCardHolder cardHolder = new PostCardHolder(postCard, mPicasso, tag);
        holders.add(cardHolder);
        return cardHolder;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onViewRecycled (RecyclerView.ViewHolder viewHolder) {
        ((PostCardHolder) viewHolder).unloadInnerViewItems();
    }

    public void pauseAll() {
        mPicasso.pauseTag(tag);
    }

    public void resumeAll() {
        mPicasso.resumeTag(tag);
    }

    public void stopAll() {
        mPicasso.cancelTag(tag);
        mPicasso.cancelTag(tag);
        for (PostCardHolder h : holders) {
            h.unloadInnerViewItems();
        }
        holders.clear();
    }
}
