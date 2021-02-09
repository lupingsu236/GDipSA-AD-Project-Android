package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class SearchRouteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceRouteFragment(null);

        ImageButton searchBtn = findViewById(R.id.searchBtn);
        AutoCompleteTextView startingStation = findViewById(R.id.starting_station);
        AutoCompleteTextView destination = findViewById(R.id.destination);

        searchBtn.setOnClickListener(v -> {

            String startingStationName = startingStation.getText().toString().trim();
            String destinationName = destination.getText().toString().trim();

            if (startingStationName.isEmpty() || destinationName.isEmpty())
            {
                Toast.makeText(this, "Please input starting station and " +
                        "destination", Toast.LENGTH_SHORT).show();
            }
            else
            {
                new Thread(() ->
                {
                    Graph graph = Graph.createMRTGraph();
                    try
                    {
                        graph.updateGraphFromWebAPI();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    Route result = Dijkstra
                            .shortestPathAndDistanceFromSourceToDestination
                                    (startingStationName, destinationName, graph);
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
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Please input valid stations/no path",
                                    Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_route;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_search;
    }

    public void replaceRouteFragment(Route route) {
        RouteFragment fragment = new RouteFragment();
        if (route!=null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable("route", route);
            fragment.setArguments(arguments);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_route_container, fragment);
        if(route!=null) {
            trans.addToBackStack(null);
        }
        trans.commit();
    }

/*    @Override
    public void onBackPressed()
    {
        //if back stack only consist of the first fragment insertion, exit app
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackCount == 1) {
            System.exit(1);
        } else {
            super.onBackPressed();
        }
    }*/
}