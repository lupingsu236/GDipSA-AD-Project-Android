package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public class NewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_news;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_news;
    }
}