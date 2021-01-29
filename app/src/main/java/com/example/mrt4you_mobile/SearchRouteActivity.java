package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

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
}