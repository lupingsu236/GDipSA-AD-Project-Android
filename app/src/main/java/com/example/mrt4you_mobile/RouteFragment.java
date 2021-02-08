package com.example.mrt4you_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RouteFragment extends Fragment {

    private boolean isRouteSaved;
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
            ListView listview = view.findViewById(R.id.listView);
            TextView route_timing = view.findViewById(R.id.route_timing);
            Bundle bundle = getArguments();
            if (bundle!=null) {
                Route route = (Route) bundle.getSerializable("route");
                isRouteSaved = checkIfSaved(route.getStart(), route.getEnd());

                int change_timing=0;
                for(Node ic : route.getInterchanges()) {
                    if (ic.getName().equals("City Hall") || ic.getName().equals("Raffles Place")) {
                        change_timing+=5;
                    } else {
                        change_timing+=7;
                    }
                }
                if (change_timing!=0) {
                    route_timing.setText(route.getTotalTime() + " + " + change_timing + " min");
                } else {
                    route_timing.setText(route.getTotalTime() + " min");
                }


                if(isRouteSaved) {
                    bookmark.setImageResource(R.drawable.ic_action_saved);
                }
                bookmark.setOnClickListener(v -> {
                    if(isRouteSaved) {
                        boolean deleted = deleteRoute(route.getStart(), route.getEnd());
                        String message;
                        if (deleted) {
                            message = "Route deleted!";
                            bookmark.setImageResource(R.drawable.ic_action_tobookmark);
                        } else {
                            message = "Error deleting!";
                        }
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                    } else {
                        boolean saved = saveRoute(route.getStart(), route.getEnd());
                        String message;
                        if (saved) {
                            message = "Route saved!";
                            bookmark.setImageResource(R.drawable.ic_action_saved);
                        } else {
                            message = "Error saving!";
                        }
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }

                    isRouteSaved = !isRouteSaved;
                });

                RouteDisplayAdapter adapter = new RouteDisplayAdapter(getActivity(), 0);
                adapter.setData(route);

                if (listview!=null) {
                    listview.setAdapter(adapter);
                }
             } else {
                //add empty fragment
                route_timing.setVisibility(View.GONE);
                bookmark.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);

            }

        }

    }

    private boolean checkIfSaved(String start, String end) {
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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
            return false;
        }

        String[] saved_routes;
        if (content!=null)  {
            saved_routes = content.split(";");
            for (String saved_route : saved_routes) {
                if (!saved_route.equals(start + "," + end)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean saveRoute(String start, String end) {
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, "saved_routes.txt");
        if (file.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(file, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(start + "," + end + ";");
                osw.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(start + "," + end + ";");
                osw.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean deleteRoute(String start, String end) {
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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
            return false;
        }

        String[] saved_routes;
        String new_saved_routes="";
        if (content!=null)  {
            saved_routes = content.split(";");
            for (String saved_route : saved_routes) {
                if (!saved_route.equals(start + "," + end)) {
                    new_saved_routes += saved_route + ";";
                }
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(new_saved_routes);
            osw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}