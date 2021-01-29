package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StarredStationsActivity extends BaseActivity {

    View addbtn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addbtn=findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StarredStationsActivity.this,AddStarredStationActivity.class);
                startActivity(intent);
            }
        });

        String addname=getIntent().getStringExtra("starname");
        String stationname=getIntent().getStringExtra("stationname");
        String linename=getIntent().getStringExtra("linename");


        FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 80, 0, 0);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0, 20, 0, 0);

        ConstraintSet set=new ConstraintSet();
        ConstraintLayout mainlayout=findViewById(R.id.addlist);
        TextView t1=new TextView(this);
        t1.setText(addname);
        t1.setId(R.id.t11);
        t1.setTextSize(18);
        t1.setLayoutParams(layoutParams1);
        mainlayout.addView(t1);
        set.connect(R.id.addlist,ConstraintSet.LEFT,R.id.addlist,ConstraintSet.RIGHT);

        TextView t2=new TextView(this);
        t2.setText(stationname);
        t2.setLayoutParams(layoutParams2);
        t2.setTextSize(18);
        mainlayout.addView(t2);
        set.connect(R.id.addlist,ConstraintSet.LEFT,R.id.addlist,ConstraintSet.RIGHT);


        ImageView view1=findViewById(R.id.i1);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_starred_stations;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_starred;
    }
}