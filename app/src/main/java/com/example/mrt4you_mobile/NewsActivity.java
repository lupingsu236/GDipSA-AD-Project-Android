package com.example.mrt4you_mobile;

import android.os.Bundle;
import android.view.View;
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
import java.util.List;

public class NewsActivity extends BaseActivity
{
    private static final String NSLINEFWDSTATION = "Jurong East";
    private static final String NSLINEOPPSTATION = "Marina South Pier";
    private static final String EWLINEFWDSTATION = "Pasir Ris";
    private static final String EWLINEOPPSTATION = "Tuas Link";
    private static final String CCLINEFWDSTATION = "Dhoby Ghaut";
    private static final String CCLINEOPPSTATION = "HarbourFront";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        updateView();
    }

    @Override
    int getContentViewId()
    {
        return R.layout.activity_news;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_news;
    }

    public void updateView() {
        new Thread(() ->
        {
            List<TextView> newsList = new ArrayList<>();
            List<TextView> timeList = new ArrayList<>();

            TextView textNews1 = findViewById(R.id.textNews1);
            newsList.add(textNews1);
            TextView textTime1 = findViewById(R.id.textTime1);
            timeList.add(textTime1);
            TextView textNews2 = findViewById(R.id.textNews2);
            newsList.add(textNews2);
            TextView textTime2 = findViewById(R.id.textTime2);
            timeList.add(textTime2);
            TextView textNews3 = findViewById(R.id.textNews3);
            newsList.add(textNews3);
            TextView textTime3 = findViewById(R.id.textTime3);
            timeList.add(textTime3);
            TextView textNews4 = findViewById(R.id.textNews4);
            newsList.add(textNews4);
            TextView textTime4 = findViewById(R.id.textTime4);
            timeList.add(textTime4);
            TextView textNews5 = findViewById(R.id.textNews5);
            newsList.add(textNews5);
            TextView textTime5 = findViewById(R.id.textTime5);
            timeList.add(textTime5);

            try
            {
                URL url = new URL("http://10.0.2.2:63414/api/News");
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

                if (content.toString().length() <= 2)
                {
                    runOnUiThread(() -> {
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");
                        textTime1.setText(now.format(df));

                        textNews1.setText(R.string.no_news);
                    });
                }
                else
                {
                    JSONArray jsonArray = new JSONArray(content.toString());
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String timeString = jsonObject.get("time").toString();
                        DateTimeFormatter dfParse = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
                        LocalDateTime time = LocalDateTime.parse(timeString, dfParse);
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");
                        int finalI = i;
                        runOnUiThread(() -> timeList.get(finalI).setText(time.format(df)));

                        String stationCode = jsonObject.get("stationCode").toString().trim();
                        String stationName = jsonObject.get("stationName").toString().trim();
                        int timeToNextStation = Integer.parseInt(jsonObject.get("timeToNextStation")
                                .toString());
                        int timeToNextStationOpp = Integer.parseInt(jsonObject
                                .get("timeToNextStationOpp").toString());
                        int currentStatus = Integer.parseInt(jsonObject.get("currentStatus").toString());
                        runOnUiThread(() ->
                                newsList.get(finalI).setText(generateNewsMsg(stationCode, stationName,
                                        timeToNextStation, timeToNextStationOpp, currentStatus)));
                    }
                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
                TextView textConnectionFailed = findViewById(R.id.textConnectionFailed);
                ScrollView scrollViewNews = findViewById(R.id.scrollViewNews);
                runOnUiThread(() -> {
                    textConnectionFailed.setVisibility(View.VISIBLE);
                    scrollViewNews.setVisibility(View.GONE);
                });
            }
        }).start();
    }

    public static String generateNewsMsg (String stationCode, String stationName,
                                          int timeToNextStation, int timeToNextStationOpp,
                                          int currentStatus)
    {
        String output = "";
        final String OPERATIONALSUFFIX = "is now fully operational.";
        final String BREAKDOWNBOTHSUFFIX = "is broken down in both directions.";
        final String DELAYEDBOTHSUFFIX = "is delayed in both directions. Travel time to the next" +
                " stations in the direction of";
        final String BREAKDOWNONEWAYSUFFIX = "is broken down in the direction of";
        final String DELAYEDONEWAYSUFFIX = "is delayed in the direction of";
        final String ONEWAYSUFFIX = "Travel time to the next station towards";
        final String NSPREFIX = "ns";
        final String EWPREFIX = "ew";
        final String CCPREFIX = "cc";

        if (currentStatus == 0)
            output = String.format("[%s] %s %s", stationCode, stationName, OPERATIONALSUFFIX);
        else
        {
            switch (currentStatus)
            {
                case 1:
                    output = String.format("[%s] %s %s", stationCode, stationName,
                            BREAKDOWNBOTHSUFFIX);
                    break;
                case 2:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, NSLINEFWDSTATION, ONEWAYSUFFIX,
                                NSLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minutes(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEFWDSTATION, ONEWAYSUFFIX,
                                EWLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minutes(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, CCLINEFWDSTATION, ONEWAYSUFFIX,
                                CCLINEOPPSTATION, timeToNextStationOpp);
                    }
                    break;
                case 3:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minutes(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, NSLINEOPPSTATION, ONEWAYSUFFIX,
                                NSLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minutes(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEOPPSTATION, ONEWAYSUFFIX,
                                EWLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minutes(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, CCLINEOPPSTATION, ONEWAYSUFFIX,
                                CCLINEFWDSTATION, timeToNextStation);
                    }
                    break;
                case 4:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("[%s] %s %s %s and %s are now %s and %s minute(s), respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, NSLINEFWDSTATION,
                                NSLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                       output = String.format("[%s] %s %s %s and %s are now %s and %s minute(s), respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, EWLINEFWDSTATION,
                                EWLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s and %s are now %s and %s minute(s), respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, CCLINEFWDSTATION,
                                CCLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    break;
                case 5:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                       output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                               stationName, DELAYEDONEWAYSUFFIX, NSLINEFWDSTATION, ONEWAYSUFFIX,
                               NSLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, EWLINEFWDSTATION, ONEWAYSUFFIX,
                                EWLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, CCLINEFWDSTATION, ONEWAYSUFFIX,
                                CCLINEFWDSTATION, timeToNextStation);
                    }
                    break;
                case 6:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, NSLINEOPPSTATION, ONEWAYSUFFIX,
                                NSLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, EWLINEOPPSTATION, ONEWAYSUFFIX,
                                EWLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is now %s minute(s).", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, CCLINEOPPSTATION, ONEWAYSUFFIX,
                                CCLINEOPPSTATION, timeToNextStationOpp);
                    }
                    break;
            }
        }
        return output;
    }
}