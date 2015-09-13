package com.willjiang.warthunderlive;

import android.content.Context;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.UI.PostCard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Will on 9/3/15.
 */
public class PostsAdapter extends ArrayAdapter {

    private List posts;

    public PostsAdapter(Context context, List posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap post = (HashMap) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.fragment_post, parent, false);
        }

        PostCard card = new PostCard(getContext());

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
        String timestamp = getDate(Long.valueOf(rawTimestamp) * 1000);
        card.setTimestamp(timestamp);

        ((CardViewNative) convertView).setCard(card);
        return convertView;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("LLLL dd, yyyy HH:mm", cal).toString();

        if (date.substring(6, 10).equals("2015")) {
            date = date.substring(0, 5) + date.substring(10);
        }
        return date;
    }



}
