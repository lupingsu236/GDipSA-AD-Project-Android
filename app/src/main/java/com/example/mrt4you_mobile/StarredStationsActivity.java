package com.example.mrt4you_mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StarredStationsActivity extends BaseActivity{

    public List<starinfo> starStation;
    ArrayAdapter<starinfo> starAdapter;
    ListView listView;
    View addbtn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        starStation = new ArrayList<>();
        starStation.add(new starinfo("Home1", "NS3: Yew Tee", "redline"));
        starStation.add(new starinfo("School", "CC24: Kent Ridge", "circleine"));
        starStation.add(new starinfo("Aunt Betty's1", "NS1: Jurong East", "redline"));
        starStation.add(new starinfo("Home2", "NS3: Yew Tee", "redline"));
        starStation.add(new starinfo("School", "CC24: Kent Ridge", "circleine"));
        starStation.add(new starinfo("Aunt Betty's2", "NS1: Jurong East", "redline"));
        starStation.add(new starinfo("Home3", "NS3: Yew Tee", "redline"));
        starStation.add(new starinfo("School", "CC24: Kent Ridge", "circleine"));
        starStation.add(new starinfo("Aunt Betty's3", "NS1: Jurong East", "redline"));
        starStation.add(new starinfo("Home4", "NS3: Yew Tee", "redline"));
        starStation.add(new starinfo("School", "CC24: Kent Ridge", "circleine"));
        starStation.add(new starinfo("Aunt Betty's4", "NS1: Jurong East", "redline"));

        starAdapter = new starAdapter(StarredStationsActivity.this, R.layout.custom_star, starStation);
        listView = findViewById(R.id.addlist);
        listView.setAdapter(starAdapter);
        listView.smoothScrollToPosition(0);


        addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StarredStationsActivity.this, AddStarredStationActivity.class);
                startActivityForResult(intent,1);
            }
        });


        String addname = getIntent().getStringExtra("starname");
        String stationname = getIntent().getStringExtra("stationname");
        String linename = getIntent().getStringExtra("linename");

        if (addname != null) {
            starinfo s = new starinfo(addname, stationname, linename);
            starStation.add(s);
            starAdapter.notifyDataSetChanged();
            listView.smoothScrollToPosition(0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            starinfo s = new starinfo(data.getStringExtra("starname"), data.getStringExtra("stationname"), data.getStringExtra("linename"));
            starStation.add(s);
            starAdapter.notifyDataSetChanged();
            listView.smoothScrollToPosition(0);
        }
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