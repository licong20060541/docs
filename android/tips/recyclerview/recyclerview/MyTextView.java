package com.example.xumin.items.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("licong10", getMeasuredHeight() + "  -- width " + getMeasuredWidth());
            }
        }, 2000);
    }
}
