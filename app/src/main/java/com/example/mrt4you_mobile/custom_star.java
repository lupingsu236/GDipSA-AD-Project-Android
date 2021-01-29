package com.example.mrt4you_mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class custom_star extends RelativeLayout {
    public custom_star(Context context, AttributeSet attrs,int defStyleAttr) {

        super(context,attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.custom_star, defStyleAttr, 0);
        int num=a.getIndexCount();
        for(int i=0;i<num;i++){
            int attr=a.getIndex(i);
            switch(attr){
                case R.styleable.custom_star_ltxt1:
                    String lefttxt1=a.getString(attr);
                    break;
                case R.styleable.custom_star_ltxt2:
                    String lefttxt2=a.getString(attr);
                    break;
                case R.styleable.custom_star_rtxt:
                    String righttxt=a.getString(attr);
                    break;
                case R.styleable.custom_star_rtxtcolor:
                    int rtxtcolor=a.getColor(attr, Color.BLACK);
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
