package com.example.mrt4you_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_map) {
                startActivity(new Intent(this, MapActivity.class));
            } else if (itemId == R.id.action_starred) {
                startActivity(new Intent(this, StarredStationsActivity.class));
            } else if (itemId == R.id.action_search) {
                startActivity(new Intent(this, SearchRouteActivity.class));
            } else if (itemId == R.id.action_saved) {
                startActivity(new Intent(this, SavedRoutesActivity.class));
            } else if (itemId == R.id.action_news) {
                startActivity(new Intent(this, NewsActivity.class));
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();
    // this is to return which layout(activity) needs to display when clicked on tabs.

    abstract int getNavigationMenuItemId();
    //Which menu item selected and change the state of that menu item
}