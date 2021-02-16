package com.example.mrt4you_mobile;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SavedRoutesActivity extends BaseActivity
        implements AdapterView.OnItemSelectedListener, RouteFragment.iRouteFragment {
    Spinner mRoutesList;
    String[] mSavedRoutes;
    ArrayAdapter<String> mArrayAdapter;
    List<String> mSavedRoutesForDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //populate list of saved routes in dropdown
        mRoutesList = findViewById(R.id.routes_list);
        String content = getSavedRoutes();
        if(content!=null) {
            mSavedRoutes = content.split(";");
            mSavedRoutesForDisplay.add(getString(R.string.select_hint));
            for (String mSavedRoute : mSavedRoutes) {
                String[] startAndEnd = mSavedRoute.split(",");
                mSavedRoutesForDisplay.add(startAndEnd[0] + "  ->  " + startAndEnd[1]);
            }
            mArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, mSavedRoutesForDisplay);
            mRoutesList.setAdapter(mArrayAdapter);
            mRoutesList.setOnItemSelectedListener(this);
        } else {
            TextView noRoutesMsg = findViewById(R.id.noRoutesMsg);
            noRoutesMsg.setVisibility(View.VISIBLE);
            mRoutesList.setVisibility(View.GONE);
            FrameLayout fragmentContainer = findViewById(R.id.fragment_route_container);
            fragmentContainer.setVisibility(View.GONE);
        }

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // do nothing if the 'please select' hint option is selected
        if (position == 0) {
            replaceRouteFragment(null);
        }
        else {
            // retrieve the selected route and generate shortest path for display via fragment
            String selected_route = mSavedRoutes[position-1];
            String[] startAndEnd = selected_route.split(",");
            new Thread(() ->
            {
                Graph graph = Graph.createMRTGraph();
                try
                {
                    graph.updateGraphFromWebAPI();
                }
                catch (JSONException | IOException e)
                {
                    e.printStackTrace();
                }
                Route result = Dijkstra
                        .shortestPathAndDistanceFromSourceToDestination
                                (startAndEnd[0], startAndEnd[1], graph);
                if (result != null)
                {
                    runOnUiThread(() -> {
                        replaceRouteFragment(result);
                        // the prints below are just to check if data is being passed properly
                        System.out.println(result.getPath());
                        System.out.println(result.getTotalTime());
                        System.out.println(result.getInterchanges().size());
                        for (Subroute sr : result.getSubroutes())
                        {
                            System.out.println(sr.getLine());
                            System.out.println("Towards " + sr.getDirection());
                            System.out.println(sr.getNoOfStations());
                            System.out.print(sr.getNoOfMins() + "\n");
                        }
                    });
                }
                else
                {
                    runOnUiThread(() -> Toast.makeText(this, "There is no path available!",
                            Toast.LENGTH_SHORT).show());
                }
            }).start();
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
        trans.commit();
    }

    @Override
    public void refreshDropdownAfterRouteDeleted() {
        // retrieve new list of saved routes and update dropdown list
        String content = getSavedRoutes();
        if(content!=null) {
            mSavedRoutes = content.split(";");
            mSavedRoutesForDisplay.clear();
            mSavedRoutesForDisplay.add(getString(R.string.select_hint));
            for (String mSavedRoute : mSavedRoutes) {
                String[] startAndEnd = mSavedRoute.split(",");
                mSavedRoutesForDisplay.add(startAndEnd[0] + "  ->  " + startAndEnd[1]);
            }
            mRoutesList.setSelection(0);
            mArrayAdapter.notifyDataSetChanged();

        }
        // if there is no saved route remaining, display message instead
        else {
            TextView noRoutesMsg = findViewById(R.id.noRoutesMsg);
            noRoutesMsg.setVisibility(View.VISIBLE);
            mRoutesList.setVisibility(View.GONE);
            FrameLayout fragmentContainer = findViewById(R.id.fragment_route_container);
            fragmentContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void replaceSearchBarsDataUponBackStackPop(Route route) {}

    @Override
    int getContentViewId() {
        return R.layout.activity_saved_routes;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_saved;
    }
}