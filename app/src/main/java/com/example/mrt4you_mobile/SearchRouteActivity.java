package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class SearchRouteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceRouteFragment();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_route;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_search;
    }

    public void replaceRouteFragment() {
        RouteFragment fragment = new RouteFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_route_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    public void onBackPressed()
    {
        //if back stack only consist of the first fragment insertion, exit app
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackCount == 1) {
            System.exit(1);
        } else {
            super.onBackPressed();
        }
    }
}