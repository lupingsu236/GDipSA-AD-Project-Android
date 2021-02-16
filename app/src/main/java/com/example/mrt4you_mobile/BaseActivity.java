package com.example.mrt4you_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigationView;
    private static String AZURE_NEWS_API_URL = "https://mrt4youweb.azurewebsites.net/api/news";
    private static String LOCAL_NEWS_API_URL = "http://10.0.2.2:63414/api/News";
    private static String AZURE_NONOPERATIONALSTATIONS_URL = "https://mrt4youweb.azurewebsites.net/api/nonoperationalstations";
    private static String LOCAL_NONOPERATIONALSTATIONS_URL = "http://10.0.2.2:63414/api/NonOperationalStations";

    // edit following two variables to switch between azure cloud and local host api
    public static final String NONOPERATIONALSTATIONS_URL = AZURE_NONOPERATIONALSTATIONS_URL;
    public static final String NEWS_URL = AZURE_NEWS_API_URL;


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
                Intent intent = new Intent(this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (itemId == R.id.action_status) {
                Intent intent = new Intent(this, LineStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (itemId == R.id.action_search) {
                Intent intent = new Intent(this, SearchRouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (itemId == R.id.action_saved) {
                Intent intent = new Intent(this, SavedRoutesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (itemId == R.id.action_news) {
                Intent intent = new Intent(this, NewsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
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