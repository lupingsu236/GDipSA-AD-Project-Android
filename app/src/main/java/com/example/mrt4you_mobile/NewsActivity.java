package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsActivity extends BaseActivity
{
    private static final String NSLINEFWDSTATION = "Jurong East";
    private static final String NSLINEOPPSTATION = "Marina South Pier";
    private static final String EWLINEFWDSTATION = "Pasir Ris";
    private static final String EWLINEOPPSTATION = "Tuas Link";
    private static final String CCLINEFWDSTATION = "Dhoby Ghaut";
    private static final String CCLINEOPPSTATION = "HarbourFront";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            updateView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_news;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_news;
    }

    public static void updateView() throws JSONException
    {
        String data = "[{\"stationCode\":\"NS02\",\"stationName\":\"Bukit Batok\"," +
                "\"timeToNextStation\":2147482647,\"timeToNextStationOpp\":2,\"currentStatus\":2," +
                "\"time\":\"9/2/2021 1:56:29 PM\"}," +
                "{\"stationCode\":\"NS03\",\"stationName\":\"Bukit Gombak\",\"timeToNextStation\"" +
                ":15,\"timeToNextStationOpp\":15,\"currentStatus\":4, \"time\":" +
                "\"10/1/2021 10:00:23 AM\"}," +
                "{\"stationCode\":\"NS01\",\"stationName\":\"Jurong East\",\"timeToNextStation\"" +
                ":0,\"timeToNextStationOpp\":10,\"currentStatus\":6," +
                "\"time\":\"2/1/2021 11:53:02 PM\"}]";
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
        }
    }

    public String generateNewsMsg (String stationCode, String stationName, int timeToNextStation, int timeToNextStationOpp, int currentStatus, String time)
    {
        String output = "";
        final String OPERATIONALSUFFIX = "is now fully operational.";
        final String BREAKDOWNBOTHSUFFIX = "is broken down in both directions";
        final String NSPREFIX = "ns";
        final String EWPREFIX = "ew";
        final String CCPREFIX = "cc";

        if (currentStatus == 0)
            output = String.format("%s %s %s", stationCode, stationName, OPERATIONALSUFFIX);
        else
        {
            switch (currentStatus)
            {
                case 1:
                    output = String.format("%s %s %s", stationCode, stationName, BREAKDOWNBOTHSUFFIX);
                    break;
                case 2:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("%s %s is broken down in the direction of %s. Travel time in the direction of %s is %s.");
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {

                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {

                    }
                    break;

            }
        }

        return output;
    }
}