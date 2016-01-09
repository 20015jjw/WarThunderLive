package com.willjiang.warthunderlive;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;
import com.willjiang.warthunderlive.Network.API;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity {

    Picasso picasso;
    SparseIntArray size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Picasso.Builder builder = new Picasso.Builder(this);
        picasso = builder.build();
        size = new SparseIntArray(2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        // author name
        String authorName = intent.getStringExtra(API.author_nickname);
        TextView authorNickname = (TextView) findViewById(R.id.post_author_header_info_nickname);
        authorNickname.setTextSize(getResources().getDimension(R.dimen.detail_font_size));
        setHeight(authorNickname, getResources().getDimensionPixelSize(R.dimen.detail_tv_height));
        authorNickname.setText(authorName);

        // timestamp
        String timestamp_str = intent.getStringExtra(API.timestamp);
        TextView timestamp = (TextView) findViewById(R.id.post_author_header_info_timestamp);
        timestamp.setTextSize(getResources().getDimension(R.dimen.detail_font_size));
        setHeight(timestamp, getResources().getDimensionPixelSize(R.dimen.detail_tv_height));
        timestamp.setText(timestamp_str);

        // author avatar
        String authorAvatarURL = intent.getStringExtra(API.author_avatar);
        ImageView authorAvatar = (ImageView) findViewById(R.id.post_author_header_avatar);
        setHeight(authorAvatar, getResources().getDimensionPixelSize(R.dimen.detail_avatar_height));
         Utils.loadImage(authorAvatar, authorAvatarURL, picasso, this.size, PostsAdapter.avatarKey);
//        picasso.load(authorAvatarURL)
//               .placeholder(R.drawable.no_avatar)
//               .into(authorAvatar);

        // description
        Spanned descriptionText = (Spanned) intent.getExtras().get(API.description);
        TextView description = (TextView) findViewById(R.id.post_detail_description);
        description.setText(descriptionText);
        description.setLinksClickable(true);
        description.setMovementMethod(LinkMovementMethod.getInstance());

        ArrayList<String> imagesURLs = intent.getStringArrayListExtra(API.images);
        if (imagesURLs != null) {
            SliderLayout images = (SliderLayout) findViewById(R.id.post_detail_image_list);
            images.stopAutoCycle();
            if (imagesURLs.size() < 2) {
                images.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            }

            for (String imageURL : imagesURLs) {
                DefaultSliderView imageSlider = new DefaultSliderView(this);
                imageSlider.image(imageURL);
                imageSlider.setScaleType(BaseSliderView.ScaleType.CenterInside);
                images.addSlider(imageSlider);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setHeight(View view, int height) {
        ViewGroup.LayoutParams prams = view.getLayoutParams();
        prams.height = height;
        view.setLayoutParams(prams);
    }
}
