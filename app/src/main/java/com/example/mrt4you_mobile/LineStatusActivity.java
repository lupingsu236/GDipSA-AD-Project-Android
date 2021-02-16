package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LineStatusActivity extends BaseActivity {
    private static String AZURENONOPERATIONALSTATIONSURL = "https://mrt4youweb.azurewebsites.net/api/nonoperationalstations";
    private static String LOCALNONOPERATIONALSTATIONSURL = "http://10.0.2.2:63414/api/NonOperationalStations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLineStatus();
        ImageButton refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(v-> updateLineStatus());
    }

    private void updateLineStatus() {
        new Thread(() ->
        {
            ImageView ns_status_display = findViewById(R.id.ns_status);
            ImageView ew_status_display = findViewById(R.id.ew_status);
            ImageView cc_status_display = findViewById(R.id.cc_status);

            Map<String, String> line_status = new HashMap<>();
            line_status.put("ns", "normal");
            line_status.put("ew", "normal");
            line_status.put("cc", "normal");

            try
            {
                URL url = new URL(AZURENONOPERATIONALSTATIONSURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(1000);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                        (urlConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    content.append(inputLine);
                }
                bufferedReader.close();
                urlConnection.disconnect();

                if(content.toString().length()<=2) {
                    runOnUiThread(() -> {
                        ns_status_display.setImageResource(R.drawable.normal);
                        ew_status_display.setImageResource(R.drawable.normal);
                        cc_status_display.setImageResource(R.drawable.normal);
                    });
                }
                else if (content.toString().length() >2)
                {
                    JSONArray jsonArray = new JSONArray(content.toString());
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String stationCode = jsonObject.get("stationCode").toString();
                        int status = Integer.parseInt(jsonObject.get("status").toString());

                        if(status!=0) {
                            //check current status of line to see if update is required
                            switch (Objects.requireNonNull(line_status.get(stationCode.substring(0, 2).toLowerCase()))) {
                                case "normal":
                                    if (status <= 3) {
                                        line_status.put(stationCode.substring(0, 2).toLowerCase(), "breakdown");
                                    } else {
                                        line_status.put(stationCode.substring(0, 2).toLowerCase(), "delayed");
                                    }
                                    break;
                                case "breakdown":
                                    //no update required if already breakdown
                                    break;
                                case "delayed":
                                    if (status <= 3) {
                                        line_status.put(stationCode.substring(0, 2).toLowerCase(), "breakdown");
                                    }
                                    break;
                            }
                        }
                    }

                    runOnUiThread(() -> {
                        //update status display for each respective line
                        switch(Objects.requireNonNull(line_status.get("ns"))){
                            case "normal":
                                ns_status_display.setImageResource(R.drawable.normal);
                                break;
                            case "breakdown":
                                ns_status_display.setImageResource(R.drawable.breakdown);
                                break;
                            case "delayed":
                                ns_status_display.setImageResource(R.drawable.delayed);
                                break;
                        }

                        switch(Objects.requireNonNull(line_status.get("ew"))){
                            case "normal":
                                ew_status_display.setImageResource(R.drawable.normal);
                                break;
                            case "breakdown":
                                ew_status_display.setImageResource(R.drawable.breakdown);
                                break;
                            case "delayed":
                                ew_status_display.setImageResource(R.drawable.delayed);
                                break;
                        }
                        switch(Objects.requireNonNull(line_status.get("cc"))){
                            case "normal":
                                cc_status_display.setImageResource(R.drawable.normal);
                                break;
                            case "breakdown":
                                cc_status_display.setImageResource(R.drawable.breakdown);
                                break;
                            case "delayed":
                                cc_status_display.setImageResource(R.drawable.delayed);
                                break;
                        }
                    });

                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
                runOnUiThread(() -> {
                    ns_status_display.setImageResource(R.drawable.connection_failed);
                    ew_status_display.setImageResource(R.drawable.connection_failed);
                    cc_status_display.setImageResource(R.drawable.connection_failed);
                });
            }
        }).start();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_line_status;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_status;
    }
}