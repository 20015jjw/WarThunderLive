/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * This class provides a simple card with Thumbnail loaded with built-in method and Ion library
 * * Please refer to https://github.com/koush/ion for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class PostCard extends Card {

    protected String mTitle;

    public PostCard(Context context) {
        this(context, R.layout.inner);
    }

    public PostCard(Context context, int innerLayout) {
        super(context, R.layout.inner);
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

        //Retrieve elements
        TextView title = (TextView) parent.findViewById(R.id.title);
        ImageView img = (ImageView) parent.findViewById(R.id.img);

        if (title != null)
            title.setText(mTitle);

        getCardThumbnail().setupInnerViewElements(parent, img);

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

            Ion.with((ImageView) viewImage)
                .resize(96, 96)
                .centerInside()
                .load("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg");


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

}
