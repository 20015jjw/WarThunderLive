package com.willjiang.warthunderlive.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by will on 1/3/16.
 */
public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout (Context context) {
        super(context);
    }

    public SquareLinearLayout (Context context, AttributeSet as) {
        super(context, as);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
