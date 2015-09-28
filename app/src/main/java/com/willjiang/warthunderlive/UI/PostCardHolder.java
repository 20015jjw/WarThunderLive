package com.willjiang.warthunderlive.UI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.R;

import java.util.HashMap;

public class PostCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected View card;
    protected String id;
    protected String language;
    protected String mDescription;
    protected String mThumbnailURL;
    protected boolean hasThumbnail;
    protected String mTimestamp;
    protected HashMap<String, String> mAuthor;

    public PostCardHolder(View card) {
        super(card);
        this.card = card;
    }

    public void setupInnerViewElements() {
        // header
        LinearLayout author = (LinearLayout) card.findViewById(R.id.post_author_header);
        LinearLayout authorInfo = (LinearLayout) author.findViewById(R.id.post_author_header_info);
        TextView authorNickname = (TextView) authorInfo.findViewById(R.id.post_author_header_info_nickname);
        // author name
        authorNickname.setText(this.mAuthor.get(API.author_nickname));
        // timestamp
        TextView TimeStamp = (TextView) authorInfo.findViewById(R.id.post_author_header_info_timestamp);
        TimeStamp.setText(mTimestamp);
        // author avatar
        ImageView authorAvatar = (ImageView) author.findViewById(R.id.post_author_header_avatar);
        Ion.with(authorAvatar)
                .placeholder(R.drawable.no_avatar)
                .error(R.drawable.no_avatar)
                .load(mAuthor.get(API.author_avatar));

        // description
        TextView description = (TextView) card.findViewById(R.id.post_description);
        description.setText(mDescription);

        // thumbnail
        if (this.hasThumbnail) {
            ImageView thumbnail = (ImageView) card.findViewById(R.id.post_thumbnail);
            Ion.with(thumbnail)
                    .load(mThumbnailURL);
        }
    }

    public void setDescription (String description) {
        this.mDescription = description;
    }

    public String getDescription () {
        return mDescription;
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
    }

    public void setTimestamp (String timestamp) {
        this.mTimestamp = timestamp;
    }

    public void onClick(View v) {
    }

}
