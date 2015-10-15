package com.willjiang.warthunderlive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;


/**
 * Created by Will on 10/15/15.
 */
public class DetailImagesAdapter extends ArrayAdapter<String>{

    public DetailImagesAdapter(Context context, int viewLayout, String[] images) {
        super(context, viewLayout, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ImageView i = (ImageView) inflater.inflate(R.layout.detail_image_thumb, null);

        Ion.with(i).placeholder(R.drawable.no_avatar).
                load(getItem(position));

        return i;
    }
}
