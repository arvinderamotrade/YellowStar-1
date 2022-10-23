package com.yellowstar.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Arvinder on 2/20/2020.
 */

public class CustomTvLight extends TextView {

    public CustomTvLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTvLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTvLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/open_sans_light.ttf");
            setTypeface(tf);
        }
    }}