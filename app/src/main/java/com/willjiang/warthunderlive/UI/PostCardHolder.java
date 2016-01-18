package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.PostDetailActivity;
import com.willjiang.warthunderlive.Adapter.PostsAdapter;
import com.willjiang.warthunderlive.R;
import com.willjiang.warthunderlive.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PostCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected View mCard;
    protected Picasso mPicasso;
    protected int mTag;
    protected String id;
    protected String language;
    protected Spanned mDescription;
    protected String mThumbnailURL;
    protected boolean hasThumbnail;
    protected String mTimestamp;
    protected HashMap<String, String> mAuthor;
    protected String authorName;
    protected String authorAvatarURL;
    protected String authorID;
    protected ArrayList<String> images;
    protected boolean is_video;

    private ImageView authorAvatar;
    private ImageView thumbnail;

    private SparseIntArray sizes;

    public PostCardHolder(View card, Picasso picasso, int tag) {
        super(card);
        this.mPicasso = picasso;
        this.mCard = card;
        this.sizes = new SparseIntArray(5);
        this.mTag = tag;
    }

    public void setupInnerViewElements() {
        // clicker
        mCard.setOnClickListener(this);

        // header
        RelativeLayout author = (RelativeLayout) mCard.findViewById(R.id.post_author_header);
        LinearLayout authorInfo = (LinearLayout) author.findViewById(R.id.post_author_header_info);
        TextView authorNickname = (TextView) authorInfo.findViewById(R.id.post_author_header_info_nickname);

        // author name
        authorNickname.setText(authorName);

        // timestamp
        TextView TimeStamp = (TextView) authorInfo.findViewById(R.id.post_author_header_info_timestamp);
        String timestamp = Utils.getTimeAgo(Long.valueOf(mTimestamp) * 1000);
        TimeStamp.setText(timestamp);

        // author avatar
        authorAvatar = (ImageView) author.findViewById(R.id.post_author_header_avatar);
        Utils.loadImage(authorAvatar, authorAvatarURL,
                mPicasso, sizes, PostsAdapter.avatarKey, mTag);

        // description
        TextView description = (TextView) mCard.findViewById(R.id.post_description);
        String summary = Utils.toSummary(mDescription, 120);
        description.setText(summary);

        // thumbnail
        if (this.hasThumbnail) {
            thumbnail = (ImageView) mCard.findViewById(R.id.post_thumbnail);
            mThumbnailURL = Utils.imageQuality(mThumbnailURL, 0);
            Utils.loadImage(thumbnail, Utils.imageQuality(mThumbnailURL, 0), mPicasso,
                    sizes, PostsAdapter.thumbnailKey, mTag);
        }
    }

    public void unloadInnerViewItems() {
        mPicasso.cancelRequest(authorAvatar);
        mPicasso.invalidate(authorAvatarURL);
        authorAvatar.setImageDrawable(null);
        if (mThumbnailURL != null) {
            mPicasso.cancelRequest(thumbnail);
            mPicasso.invalidate(mThumbnailURL);
            thumbnail.setImageDrawable(null);
        }
        RelativeLayout author = (RelativeLayout) mCard.findViewById(R.id.post_author_header);
        TextView authorNickname = (TextView) author.findViewById(R.id.post_author_header_info_nickname);
        authorNickname.setText(null);
        TextView TimeStamp = (TextView) author.findViewById(R.id.post_author_header_info_timestamp);
        TimeStamp.setText(null);
        TextView description = (TextView) mCard.findViewById(R.id.post_description);
        description.setText(null);
    }

    public void setDescription (Spanned description) {
        this.mDescription = description;
    }

    public Spanned getDescription () {
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
    }

    public void setTimestamp (String timestamp) {
        this.mTimestamp = timestamp;
    }

    public void setIs_video(boolean is_video) {
        this.is_video = is_video;
    }

    @Override
    public void onClick(View v) {
        Context context = this.mCard.getContext();
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(API.author_nickname, authorName);
        intent.putExtra(API.author_avatar, authorAvatarURL);
        intent.putExtra(API.timestamp, mTimestamp);
        intent.putExtra(API.description, mDescription);
        intent.putStringArrayListExtra(API.images, images);
        if (is_video) {
            intent.putExtra(API.video_src, mThumbnailURL);
        }

        context.startActivity(intent);
    }

}
