package com.example.mrt4you_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class RouteFragment extends Fragment {

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        String route = "This is the routefragment";
        View view = getView();
        if(view!=null) {
            Bundle bundle = getArguments();
            if (bundle!=null) {
                route = bundle.getString("route");
                ImageButton bookmark = view.findViewById(R.id.bookmark);
                bookmark.setVisibility(View.VISIBLE);
                ImageButton tobookmark = view.findViewById(R.id.tobookmark);
                tobookmark.setVisibility(View.GONE);
             }
           TextView fragment_text = view.findViewById(R.id.textinput_placeholder);
           fragment_text.setText(route);

           Button searchBtn = view.findViewById(R.id.searchBtn);
           AutoCompleteTextView startingStation = view.findViewById(R.id.starting_station);
           AutoCompleteTextView destination = view.findViewById(R.id.destination);

           searchBtn.setOnClickListener(v -> {

               String startingStationName = startingStation.getText().toString();
               String destinationName = destination.getText().toString();

               if (startingStationName.isEmpty() || destinationName.isEmpty())
               {
                   Toast.makeText(getContext(), "Please input starting station and " +
                           "destination", Toast.LENGTH_SHORT).show();
               }
               else
               {
                    Graph graph = Graph.createMRTGraph();
                    Map<String, Integer> pathData = Dijkstra.
                            shortestPathAndDistanceFromSourceToDestination(startingStationName,
                                    destinationName, graph);
                    if (pathData != null) {
                        String path = pathData.keySet().stream().findFirst().get();
                        int timeTaken = pathData.get(pathData.keySet().stream().findFirst().get());
                        fragment_text.setText(String.format("Path: %s\nTime taken: %s",
                                path, timeTaken));

                        // the prints below are just to check if data is being passed properly
                        System.out.println(pathData.keySet().stream().findFirst().get());
                        System.out.println(pathData.get(pathData.keySet().stream().findFirst().get()));
                    }
                    else
                        Toast.makeText(getContext(), "Please input valid stations",
                                Toast.LENGTH_SHORT).show();
               }
           });

        }

    }
}