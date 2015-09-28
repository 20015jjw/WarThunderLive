package com.willjiang.warthunderlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.willjiang.warthunderlive.Network.API;

import java.util.ArrayList;
import java.util.zip.Inflater;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
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
        Ion.with(authorAvatar)
                .placeholder(R.drawable.no_avatar)
                .error(R.drawable.no_avatar)
                .load(authorAvatarURL);

        // description
        String descriptionText = intent.getStringExtra(API.description);
        TextView description = (TextView) findViewById(R.id.post_detail_description);
        description.setText(descriptionText);

        ArrayList<String> imagesURLs = intent.getStringArrayListExtra(API.images);
        LinearLayout images = (LinearLayout) findViewById(R.id.post_detail_image_list);
        LayoutInflater inflater = getLayoutInflater();
        for (String imagesURL : imagesURLs) {
            ImageView image = (ImageView) inflater.inflate(R.layout.post_image_container, images, false);
            images.addView(image);
            if (imagesURL.length() > 80) {
                imagesURL = Utils.imageQuality(imagesURL, 2);
            }
            Ion.with(image)
                .load(imagesURL);
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
