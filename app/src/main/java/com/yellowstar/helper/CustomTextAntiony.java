package com.yellowstar.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Asus on 6/28/2020.
 */


public class CustomTextAntiony extends TextView {

    public CustomTextAntiony(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextAntiony(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextAntiony(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ARJULIAN.ttf");
            setTypeface(tf);
        }
    }}