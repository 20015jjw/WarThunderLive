package com.willjiang.warthunderlive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Will on 9/3/15.
 */
public class PostCard extends Card {

    protected String mTitle;
    protected String mSecondaryTitle;
    protected int count;

    public PostCard(Context context) {
        this(context, R.layout.inner);
    }

    public PostCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        //Add thumbnail
        IonCardThumbnail cardThumbnail = new IonCardThumbnail(mContext);
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

        //Retrieve elements
        TextView title = (TextView) parent.findViewById(R.id.carddemo_extra_picasso_main_inner_title);
        TextView secondaryTitle = (TextView) parent.findViewById(R.id.carddemo_extra_picasso_main_inner_secondaryTitle);

        if (title != null)
            title.setText(mTitle);

        if (secondaryTitle != null)
            secondaryTitle.setText(mSecondaryTitle);

    }

    /**
     * CardThumbnail which uses Ion Library.
     * If you use an external library you have to provide your login inside #setupInnerViewElements.
     *
     * This method is called before built-in method.
     * If {@link it.gmariotti.cardslib.library.internal.CardThumbnail#isExternalUsage()} is false it uses the built-in method.
     */
    class IonCardThumbnail extends CardThumbnail {

        public IonCardThumbnail(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {

            /*
             * If your cardthumbnail uses external library you have to provide how to load the image.
             * If your cardthumbnail doesn't use an external library it will use a built-in method
             */

            //Here you have to set your image with an external library
            //Only for test, use a Resource Id and a Url

            //It is just an example !
/*
            if (((IonCard) getParentCard()).getCount() % 2 == 0) {
                Ion.with((ImageView) viewImage)
                        .resize(96, 96)
                        .error(R.drawable.ic_error_loadingsmall)
                        .centerInside()
                        .load("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg");
            } else {
                //I can use built-in method with drawable resources.
                setExternalUsage(false);
                setDrawableResource(R.drawable.ic_tris);
            }
*/
            /*
            viewImage.getLayoutParams().width = 96;
            viewImage.getLayoutParams().height = 96;
            */
        }
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSecondaryTitle() {
        return mSecondaryTitle;
    }

    public void setSecondaryTitle(String secondaryTitle) {
        mSecondaryTitle = secondaryTitle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
