package com.willjiang.warthunderlive;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.UI.PostCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        // this.posts = posts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap post = (HashMap) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.fragment_post, parent, false);
        }

        Card card = new PostCard(getContext());
        card.setTitle("WT666");

        ((CardViewNative) convertView).setCard(card);

        String rawText = (String) post.get("description");


        // TextView tvDescription = (TextView) convertView.findViewById(R.id.post_description);
        // String rawText = (String) post.get("description");
        // tvDescription.setText(Html.fromHtml(rawText));

        // ImageView tvThunbnail = (ImageView) convertView.findViewById(R.id.post_image);
        // String imageURL = (String) ((ArrayList) post.get("images")).get(0);
        // Ion.with(tvThunbnail)
        //         .load(imageURL);

        return convertView;
    }

}
