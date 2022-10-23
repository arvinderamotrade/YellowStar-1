package com.yellowstar.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Arvinder on 2/20/2020.
 */

public class CustomTVBold extends TextView {

    public CustomTVBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTVBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTVBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sf_pro.ttf");
            setTypeface(tf);
        }
    }}