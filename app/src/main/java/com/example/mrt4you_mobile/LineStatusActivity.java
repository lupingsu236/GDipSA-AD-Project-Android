package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LineStatusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_line_status;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_status;
    }
}