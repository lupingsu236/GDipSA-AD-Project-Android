package com.example.mrt4you_mobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class NewsDisplayAdapter extends ArrayAdapter
{
    private final Context CONTEXT;
    protected NewsActivity.News[] newsArr;

    public NewsDisplayAdapter(Context context,int resId)
    {
        super(context, resId);
        CONTEXT = context;
    }

    public void setData(NewsActivity.News[] newsArr)
    {
        this.newsArr = newsArr;
        for (int i = 0; i < newsArr.length; i++)
        {
            add(null);
        }
    }

    public View getView(int pos, View view, @NonNull ViewGroup parent)
    {
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) CONTEXT.getSystemService
                    (Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.news_row, parent, false);
        }

        // set image for newsImage
        ImageView newsImage = view.findViewById(R.id.newsImage);
        newsImage.setImageResource(newsArr[pos].getImage());

        // set time for newsTime
        TextView newsTime = view.findViewById(R.id.newsTime);
        newsTime.setText(newsArr[pos].getTime());

        // set text for newsMsg
        TextView newsMsg = view.findViewById(R.id.newsMsg);
        newsMsg.setText(newsArr[pos].getMsg());

        return view;
    }
}
