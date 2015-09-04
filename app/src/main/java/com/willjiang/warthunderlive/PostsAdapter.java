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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Will on 9/3/15.
 */
public class PostsAdapter extends ArrayAdapter {

    private List posts;

    public PostsAdapter(Context context, List posts) {
        super(context, 0, posts);
        // this.posts = posts;
    }

//    public void addAll(List posts) {
//        Log.v("addAll", "started");
//        this.posts = posts;
//        notifyDataSetChanged();
//    }
//
//    public void clear () {
//        this.posts.clear();
//        notifyDataSetChanged();
//    }
//
//    public HashMap getItem(int position) {
//        return (HashMap) posts.get(position);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap post = (HashMap) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.fragment_post, parent, false);
        }

        TextView tvDescription = (TextView) convertView.findViewById(R.id.list_item_description);
        String rawText = (String) post.get("description");
        tvDescription.setText(Html.fromHtml(rawText));

        ImageView tvThunbnail = (ImageView) convertView.findViewById(R.id.list_item_image);
        String imageURL = (String) ((ArrayList) post.get("images")).get(0);
        Ion.with(tvThunbnail)
                .load(imageURL);

        return convertView;
    }

}
