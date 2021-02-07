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



public class RouteDisplayAdapter extends ArrayAdapter {
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

            // if we are not responsible for adding the view to the parent,
            // then attachToRoot should be 'false' (which is in our case)
            view = inflater.inflate(R.layout.route_list_item, parent, false);
        }

        // set all necessary views
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
            start = route.getSubroutes().get(pos-1).getStations().get(route.getSubroutes().get(pos-1).getStations().size()-1);
        }

        subroute_start.setText(start.getName());


        TextView subroute_direction = view.findViewById(R.id.subroute_direction);
        subroute_direction.setText("Towards " + subroute.getDirection());

        TextView subroute_stops = view.findViewById(R.id.subroute_stops);
        subroute_stops.setText("Ride " + subroute.getNoOfStations() + " Stops (" + subroute.getNoOfMins() + " min)");

        TextView subroute_destination = view.findViewById(R.id.subroute_destination);
        Node end = subroute.getStations().get(subroute.getStations().size()-1);
        subroute_destination.setText(end.getName());


        return view;
    }
}
