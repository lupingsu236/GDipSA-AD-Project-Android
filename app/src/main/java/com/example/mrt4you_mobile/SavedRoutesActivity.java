package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SavedRoutesActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner mRoutesList;
    String[] mSavedRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //populate list of saved routes in dropdown
        mRoutesList = findViewById(R.id.routes_list);
        String content = getSavedRoutes();
        mSavedRoutes = content.split(";");
        String[] savedRoutesForDisplay = new String[mSavedRoutes.length];
        for(int i=0; i<mSavedRoutes.length; i++) {
            String[] startAndEnd = mSavedRoutes[i].split(",");
            savedRoutesForDisplay[i] = startAndEnd[0] + "  ->  " + startAndEnd[1];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, savedRoutesForDisplay);
        mRoutesList.setAdapter(adapter);
        mRoutesList.setOnItemSelectedListener(this);

    }

    private String getSavedRoutes() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, "saved_routes.txt");
        String content;
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            content = br.readLine();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return content;

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
        String selected_route = mSavedRoutes[position];
        String[] startAndEnd = selected_route.split(",");
        Graph graph = Graph.createMRTGraph();
        Route result = Dijkstra.
                shortestPathAndDistanceFromSourceToDestination(startAndEnd[0],
                        startAndEnd[1], graph);
        if (result != null) {
            replaceRouteFragment(result);

            // the prints below are just to check if data is being passed properly
            System.out.println(result.getPath());
            System.out.println(result.getTotalTime());
            System.out.println(result.getInterchanges().size());
            for (Subroute sr:result.getSubroutes()) {
                System.out.println(sr.getLine());
                System.out.println("Towards " + sr.getDirection());
                System.out.println(sr.getNoOfStations());
                System.out.print(sr.getNoOfMins() + "\n");
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void replaceRouteFragment(Route selected_route) {
        RouteFragment fragment = new RouteFragment();
        if (selected_route!=null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable("route", selected_route);
            fragment.setArguments(arguments);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_route_container, fragment);
        if(selected_route!=null) {
            trans.addToBackStack(null);
        }
        trans.commit();
    }

}