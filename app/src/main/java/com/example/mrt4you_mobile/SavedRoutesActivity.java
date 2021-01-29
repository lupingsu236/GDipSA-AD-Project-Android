package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SavedRoutesActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner mRoutesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //populate list of saved routes in dropdown
        mRoutesList = findViewById(R.id.routes_list);
        //temp list of saved routes for the drown down
        String[] routes = new String[]{"Home -> School", "Home -> Grandma", "Home -> Bugis"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, routes);
        mRoutesList.setAdapter(adapter);
        mRoutesList.setOnItemSelectedListener(this);

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_saved_routes;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_saved;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected_route = (String) parent.getItemAtPosition(position);
        replaceRouteFragment(selected_route);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void replaceRouteFragment(String selected_route) {
        Bundle arguments = new Bundle();
        arguments.putString("route", selected_route);
        RouteFragment fragment = new RouteFragment();
        fragment.setArguments(arguments);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_route_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }
}