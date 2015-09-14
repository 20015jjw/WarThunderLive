package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.ImageViewBuilder;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.PostDetailActivity;
import com.willjiang.warthunderlive.R;

import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

public class PostCard extends Card {

    protected String mDescription;
    protected String mThumbnailURL;
    protected boolean hasThumbnail;
    protected String mTimestamp;
    protected HashMap<String, String> mAuthor;

    public PostCard(Context context) {
        this(context, R.layout.card_post);
    }

    public PostCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        //Add thumbnail
        final IonCardThumbnail cardThumbnail = new IonCardThumbnail(mContext);
        cardThumbnail.setExternalUsage(true);
        addCardThumbnail(cardThumbnail);

        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                enterDetailView();
            }
        });
    }

    private void enterDetailView () {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        LinearLayout author = (LinearLayout) parent.findViewById(R.id.post_author_header);
        LinearLayout authorInfo = (LinearLayout) author.findViewById(R.id.post_author_header_info);
        TextView authorNickname = (TextView) authorInfo.findViewById(R.id.post_author_header_info_nickname);
        TextView TimeStamp = (TextView) authorInfo.findViewById(R.id.post_author_header_info_timestamp);
        ImageView authorAvatar = (ImageView) author.findViewById(R.id.post_author_header_avatar);

        // header
        // author name
        authorNickname.setText(this.mAuthor.get(API.author_nickname));
        // timestamp
        TimeStamp.setText(mTimestamp);
        // author avatar
        ((IonCardThumbnail) getCardThumbnail()).setThumbnailURL(this.mAuthor.get(API.author_avatar));
        ((IonCardThumbnail) getCardThumbnail()).setThumbnailType("avatar");
        getCardThumbnail().setupInnerViewElements(parent, authorAvatar);

        // description
        TextView description = (TextView) parent.findViewById(R.id.post_description);
        description.setText(mDescription);

        // thumbnail
        if (this.hasThumbnail) {
            ImageView thumbnail = (ImageView) parent.findViewById(R.id.post_thumbnail);
            ((IonCardThumbnail) getCardThumbnail()).setThumbnailURL(mThumbnailURL);
            ((IonCardThumbnail) getCardThumbnail()).setThumbnailType("content");
            getCardThumbnail().setupInnerViewElements(parent, thumbnail);
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

    public void setAuthor (HashMap author) {
        this.mAuthor = author;
    }

    public void setTimestamp (String timestamp) {
        this.mTimestamp = timestamp;
    }

    class IonCardThumbnail extends CardThumbnail {

        protected String mThumbnailURL;
        private String mType;

        public IonCardThumbnail(Context context) {
            super(context);
        }

        public void setThumbnailType(String type) {
            this.mType = type;
        }

        public void setThumbnailURL(String URL) {
            this.mThumbnailURL = URL;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            if (this.mType.equals("avatar")) {

                Ion.with((ImageView) viewImage)
                        .placeholder(R.drawable.no_avatar)
                        .error(R.drawable.no_avatar)
                        .load(this.mThumbnailURL);
            } else {
                Ion.with((ImageView) viewImage)
                        .load(this.mThumbnailURL);
            }
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }
    }
}
