package com.example.cozma.weatherapp.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by cozma on 03.11.2017.
 */

public class TextViewOpenSans extends TextView {
    public TextViewOpenSans(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
        this.setTextScaleX(1f);
    }
}
