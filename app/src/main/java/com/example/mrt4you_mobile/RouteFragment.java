package com.example.mrt4you_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


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

        }

    }
}