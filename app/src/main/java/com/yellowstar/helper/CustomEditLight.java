package com.yellowstar.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by asus on 10/6/2018.
 */

@SuppressLint("AppCompatCustomView")
public class CustomEditLight extends EditText {

    public CustomEditLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomEditLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditLight(Context context)
    {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/opensans_light.ttf");

            setTypeface(tf);
        }
    }}