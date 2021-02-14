package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class LineStatusActivity extends BaseActivity {
    private static String AZURENONOPERATIONALSTATIONSURL = "https://mrt4youweb.azurewebsites.net/api/nonoperationalstations";
    private static String LOCALNONOPERATIONALSTATIONSURL = "http://10.0.2.2:63414/api/NonOperationalStations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLineStatus();
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

                if (content.toString().length() >2)
                {
                    JSONArray jsonArray = new JSONArray(content.toString());
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String stationCode = jsonObject.get("stationCode").toString();
                        int status = Integer.parseInt(jsonObject.get("status").toString());

                        if(status!=0) {
                            //check current status of line to see if update is required
                            switch (line_status.get(stationCode.substring(0, 2).toLowerCase())) {
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
                        if(!line_status.get("ns").equalsIgnoreCase("normal")) {
                           if(line_status.get("ns").equalsIgnoreCase("breakdown")) {
                               ns_status_display.setImageResource(R.drawable.breakdown);
                           } else {
                               ns_status_display.setImageResource(R.drawable.delayed);
                           }
                        }
                        if(!line_status.get("ew").equalsIgnoreCase("normal")) {
                            if(line_status.get("ew").equalsIgnoreCase("breakdown")) {
                                ns_status_display.setImageResource(R.drawable.breakdown);
                            } else {
                                ns_status_display.setImageResource(R.drawable.delayed);
                            }
                        }
                        if(!line_status.get("cc").equalsIgnoreCase("normal")) {
                            if(line_status.get("cc").equalsIgnoreCase("breakdown")) {
                                ns_status_display.setImageResource(R.drawable.breakdown);
                            } else {
                                ns_status_display.setImageResource(R.drawable.delayed);
                            }
                        }
                    });

                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
                runOnUiThread(() -> {
                    ns_status_display.setImageResource(R.drawable.internet_required);
                    ew_status_display.setImageResource(R.drawable.internet_required);
                    cc_status_display.setImageResource(R.drawable.internet_required);
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