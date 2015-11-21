package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.PostDetailActivity;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PostCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected View card;
    protected Picasso thumb_picasso;
    protected Picasso avatar_picasso;
    protected String id;
    protected String language;
    protected String mDescription;
    protected String mThumbnailURL;
    protected boolean hasThumbnail;
    protected String mTimestamp;
    protected HashMap<String, String> mAuthor;
    protected String authorName;
    protected String authorAvatarURL;
    protected String authorID;
    protected ArrayList<String> images;

    private boolean loading_ordered;

    public PostCardHolder(View card, Picasso thumb_picasso, Picasso avatar_picasso) {
        super(card);
        this.card = card;
        this.thumb_picasso = thumb_picasso;
        this.avatar_picasso = avatar_picasso;
    }

    public void setupInnerViewElements() {
        // clicker
        card.setOnClickListener(this);

        // header
        LinearLayout author = (LinearLayout) card.findViewById(R.id.post_author_header);
        LinearLayout authorInfo = (LinearLayout) author.findViewById(R.id.post_author_header_info);
        TextView authorNickname = (TextView) authorInfo.findViewById(R.id.post_author_header_info_nickname);
        // author name
        authorNickname.setText(authorName);
        // timestamp
        TextView TimeStamp = (TextView) authorInfo.findViewById(R.id.post_author_header_info_timestamp);
        TimeStamp.setText(mTimestamp);
        // author avatar
        ImageView authorAvatar = (ImageView) author.findViewById(R.id.post_author_header_avatar);

        Utils.loadImage(authorAvatar, authorAvatarURL, avatar_picasso);

        // description
        TextView description = (TextView) card.findViewById(R.id.post_description);
        String summary = Utils.toSummary(mDescription, 120);
        description.setText(summary);

        // thumbnail
        if (this.hasThumbnail) {
            ImageView thumbnail = (ImageView) card.findViewById(R.id.post_thumbnail);

            Utils.loadImage(thumbnail, mThumbnailURL, thumb_picasso);
            Log.v("img_link", mThumbnailURL);
        }

//        this.loading_ordered = true;
    }

    public void setDescription (String description) {
        this.mDescription = description;
    }

    public String getDescription () {
        return mDescription;
    }

    public void setImages(ArrayList<String> images) {
        this.hasThumbnail = true;
        this.images = images;
    }
    public void setThumbnailURL (String thumbnailURL) {
        this.hasThumbnail = true;
        this.mThumbnailURL = thumbnailURL;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAuthor (HashMap author) {
        this.mAuthor = author;
        updateAuthorInfo();
    }

    private void updateAuthorInfo() {
        this.authorAvatarURL = mAuthor.get(API.author_avatar);
        this.authorName = mAuthor.get(API.author_nickname);
//        this.authorID = mAuthor.get(API.author_id);
    }

    public void setTimestamp (String timestamp) {
        this.mTimestamp = timestamp;
    }

    @Override
    public void onClick(View v) {
        Context context = this.card.getContext();
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(API.author_nickname, authorName);
        intent.putExtra(API.author_avatar, authorAvatarURL);
        intent.putExtra(API.timestamp, mTimestamp);
        intent.putExtra(API.description, mDescription);
        intent.putStringArrayListExtra(API.images, images);
        context.startActivity(intent);
    }

}
