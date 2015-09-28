package com.willjiang.warthunderlive;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.UI.PostCardHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.view.CardViewNative;
import it.gmariotti.cardslib.library.view.helper.CardViewHelper;

/**
 * Created by Will on 9/3/15.
 */
public class PostsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List posts;

    public PostsAdapter(Context context, List posts) {
        this.context = context;
        this.posts = posts;
    }

    public void addAll(List list) {
        this.posts.addAll(list);
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
        card.setDescription(Html.fromHtml(rawText).toString());

        // thumbnail
        ArrayList images = (ArrayList) post.get(API.images);
        String video_image_src = (String) post.get(API.video_info);
        if (!images.isEmpty()) {
            String imageURL = (String) (images).get(0);
            card.setThumbnailURL(imageURL);
        }
        if (video_image_src != null) {
            card.setThumbnailURL(video_image_src);
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
        View postCard = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_post, null);
        PostCardHolder cardHolder = new PostCardHolder(postCard);
        return cardHolder;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
