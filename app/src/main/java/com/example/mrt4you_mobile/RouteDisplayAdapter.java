package com.example.mrt4you_mobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


public class RouteDisplayAdapter extends ArrayAdapter<Subroute> {
    private final Context context;
    private Route route;

    public RouteDisplayAdapter(Context context, int resId) {
        super(context, resId);
        this.context = context;
    }
    public void setData(Route route) {
        this.route = route;

        for (int i=0; i<route.getSubroutes().size(); i++) {
            add(null);
        }
    }


    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.route_list_item, parent, false);
        }

        // set all necessary images and text for each subroute
        Subroute subroute = route.getSubroutes().get(pos);
        ImageView line_subroute= view.findViewById(R.id.line_subroute);
        int id1 = context.getResources().getIdentifier(subroute.getLine().toLowerCase()+"_subroute",
                "drawable", context.getPackageName());
        line_subroute.setImageResource(id1);

        ImageView line_label= view.findViewById(R.id.line_label);
        int id2 = context.getResources().getIdentifier(subroute.getLine().toLowerCase()+"_label",
                "drawable", context.getPackageName());
        line_label.setImageResource(id2);


        TextView subroute_start = view.findViewById(R.id.subroute_start);
        Node start;
        if (pos==0) {
           start = subroute.getStations().get(0);
        } else {
            start = route.getInterchanges().get(pos-1);
        }
        // find appropriate station code for display
        String start_stationCode = getStationCodeForLine(subroute.getLine(), start);
        subroute_start.setText(String.format("%s: %s", start_stationCode, start.getName()));

        TextView subroute_direction = view.findViewById(R.id.subroute_direction);
        subroute_direction.setText(String.format("Towards %s", subroute.getDirection()));

        TextView subroute_stops = view.findViewById(R.id.subroute_stops);
        subroute_stops.setText(String.format("> Ride %s Stops(s) (%s min)",
                subroute.getNoOfStations(), subroute.getNoOfMins()));


        TextView subroute_destination = view.findViewById(R.id.subroute_destination);
        Node end = subroute.getStations().get(subroute.getStations().size()-1);
        // find appropriate station code for display
        String end_stationCode = getStationCodeForLine(subroute.getLine(), end);
        subroute_destination.setText(String.format("%s: %s", end_stationCode, end.getName()));


        // if interchange exists and it's not the final subroute
        // display icon and text to indicate a change in line is required
        if(route.getInterchanges().size()>0 && pos!=route.getSubroutes().size()-1) {
            ImageView change_icon = view.findViewById(R.id.change_icon);
            change_icon.setVisibility(View.VISIBLE);
            ConstraintLayout change_info = view.findViewById(R.id.change_info);
            change_info.setVisibility(View.VISIBLE);
            /*TextView change = view.findViewById(R.id.change);
            change.setText("Change");*/
        }

        return view;
    }

    private String getStationCodeForLine(String line, Node station) {
        // returns the respective station code for the current line;
        // required to handle interchanges, which have more than one station code
        if (station.getStationCode().size()==1) {
            return station.getStationCode().get(0);
        } else {
            for (String sc : station.getStationCode()) {
                if (sc.substring(0,2).equals(line)) {
                    return sc;
                }
            }
        }
        return "";
    }
}
