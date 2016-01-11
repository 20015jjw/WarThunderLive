package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.willjiang.warthunderlive.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by will on 1/10/16.
 */
public class CustomImageSliderView extends BaseSliderView {

    public CustomImageSliderView (Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.image_slider, null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }

}
