package com.example.mrt4you_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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

        View view = getView();
        if(view!=null) {
            ImageButton bookmark = view.findViewById(R.id.bookmark);
            ImageButton tobookmark = view.findViewById(R.id.tobookmark);
            ListView listview = view.findViewById(R.id.listView);
            Bundle bundle = getArguments();
            if (bundle!=null) {
                Route route = (Route) bundle.getSerializable("route");
                boolean isRouteSaved = bundle.getBoolean("isRoutedSaved");

                if(isRouteSaved) {
                    bookmark.setVisibility(View.VISIBLE);
                    tobookmark.setVisibility(View.GONE);
                }

                RouteDisplayAdapter adapter = new RouteDisplayAdapter(getActivity(), 0);
                adapter.setData(route);

                if (listview!=null) {
                    listview.setAdapter(adapter);
                }
             } else {
                //add empty fragment
                tobookmark.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);

            }

        }

    }
}