package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.R;

import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

public class PostCard extends Card {

    protected String mDescription;
    protected String mThumbnailURL;
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
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_SHORT).show();
            }
        });
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
        getCardThumbnail().setupInnerViewElements(parent, authorAvatar);

        // description
        TextView description = (TextView) parent.findViewById(R.id.post_description);
        description.setText(mDescription);

        // thumbnail
        ImageView thumbnail = (ImageView) parent.findViewById(R.id.post_thumbnail);
        ((IonCardThumbnail) getCardThumbnail()).setThumbnailURL(mThumbnailURL);
        getCardThumbnail().setupInnerViewElements(parent, thumbnail);
    }

    public void setDescription (String description) {
        this.mDescription = description;
    }

    public String getDescription () {
        return mDescription;
    }

    public void setThumbnailURL (String thumbnailURL) {
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

        public IonCardThumbnail(Context context) {
            super(context);
        }

        public void setThumbnailURL(String URL) {
            this.mThumbnailURL = URL;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            Ion.with((ImageView) viewImage)
                    .load(this.mThumbnailURL);
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }
    }
}
