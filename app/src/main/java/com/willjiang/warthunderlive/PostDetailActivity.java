package com.willjiang.warthunderlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Picasso.Builder builder = new Picasso.Builder(this);
        picasso = builder.build();

        LinearLayout wrapper = (LinearLayout) findViewById(R.id.post_detail_image_list_wrapper);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        // author
        String authorName = intent.getStringExtra(API.author_nickname);
        TextView authorNickname = (TextView) findViewById(R.id.post_detail_author_header_info_nickname);

        // author name
        authorNickname.setText(authorName);

        // timestamp
        String timestamp = intent.getStringExtra(API.timestamp);
        TextView TimeStamp = (TextView) findViewById(R.id.post_detail_author_header_info_timestamp);
        TimeStamp.setText(timestamp);

        // author avatar
        String authorAvatarURL = intent.getStringExtra(API.author_avatar);
        ImageView authorAvatar = (ImageView) findViewById(R.id.post_detail_author_header_avatar);
        picasso.load(authorAvatarURL)
               .placeholder(R.drawable.no_avatar)
               .into(authorAvatar);

        // description
        Spanned descriptionText = (Spanned) intent.getExtras().get(API.description);
        TextView description = (TextView) findViewById(R.id.post_detail_description);
        description.setText(descriptionText);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
