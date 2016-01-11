package com.willjiang.warthunderlive;

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
import com.willjiang.warthunderlive.UI.PostCardHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List posts;
    protected Picasso thumb_picasso;
    protected Picasso avatar_picasso;
    protected int tag;

    public static final int thumbnailKey = 1;
    public static final int avatarKey = 2;

    public PostsAdapter(Context context, List posts, int tag) {
        this.context = context;
        this.posts = posts;
        this.thumb_picasso = new Picasso.Builder(context).build();
        this.avatar_picasso = new Picasso.Builder(context).build();
        this.tag = tag;
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
        String timestamp = Utils.getDate(Long.valueOf(rawTimestamp) * 1000);
        card.setTimestamp(timestamp);

        card.setupInnerViewElements();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View postCard = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_postcard, null);
        PostCardHolder cardHolder = new PostCardHolder(postCard, thumb_picasso, avatar_picasso, tag);
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
        avatar_picasso.pauseTag(tag);
        thumb_picasso.pauseTag(tag);
    }

    public void resumeAll() {
        avatar_picasso.resumeTag(tag);
        thumb_picasso.resumeTag(tag);
    }
}
