package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StarredStationsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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