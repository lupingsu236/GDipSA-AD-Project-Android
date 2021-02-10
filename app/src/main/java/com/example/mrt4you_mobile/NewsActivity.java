package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

    public void updateView() throws JSONException
    {
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

            try
            {
                URL url = new URL("http://10.0.2.2:63414/api/News");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(1000);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                        (urlConnection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    content.append(inputLine);
                }
                bufferedReader.close();
                urlConnection.disconnect();

                JSONArray jsonArray = new JSONArray(content.toString());
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String time = jsonObject.get("time").toString();
                    int finalI = i;
                    runOnUiThread(() ->
                    {
                        timeList.get(finalI).setText(time);
                    });

                    String stationCode = jsonObject.get("stationCode").toString();
                    String stationName = jsonObject.get("stationName").toString();
                    int timeToNextStation = Integer.parseInt(jsonObject.get("timeToNextStation")
                            .toString());
                    int timeToNextStationOpp = Integer.parseInt(jsonObject
                            .get("timeToNextStationOpp").toString());
                    int currentStatus = Integer.parseInt(jsonObject.get("currentStatus").toString());
                    runOnUiThread(() ->
                    {
                        newsList.get(finalI).setText(generateNewsMsg(stationCode, stationName,
                                timeToNextStation, timeToNextStationOpp, currentStatus));
                    });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    public static String generateNewsMsg (String stationCode, String stationName,
                                          int timeToNextStation, int timeToNextStationOpp,
                                          int currentStatus)
    {
        String output = "";
        final String OPERATIONALSUFFIX = "is now fully operational.";
        final String BREAKDOWNBOTHSUFFIX = "is broken down in both directions";
        final String DELAYEDBOTHSUFFIX = "is delayed in both directions. Travel time to the next" +
                " stations in the direction of";
        final String BREAKDOWNONEWAYSUFFIX = "is broken down in the direction of";
        final String DELAYEDONEWAYSUFFIX = "is delayed in the direction of";
        final String ONEWAYSUFFIX = "Travel time to the next station towards";
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
                    output = String.format("%s %s %s", stationCode, stationName,
                            BREAKDOWNBOTHSUFFIX);
                    break;
                case 2:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, NSLINEFWDSTATION, ONEWAYSUFFIX,
                                NSLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEFWDSTATION, ONEWAYSUFFIX,
                                EWLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, CCLINEFWDSTATION, ONEWAYSUFFIX,
                                CCLINEOPPSTATION, timeToNextStationOpp);
                    }
                    break;
                case 3:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, NSLINEOPPSTATION, ONEWAYSUFFIX,
                                NSLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEOPPSTATION, ONEWAYSUFFIX,
                                EWLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is %s.", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, CCLINEOPPSTATION, ONEWAYSUFFIX,
                                CCLINEFWDSTATION, timeToNextStation);
                    }
                    break;
                case 4:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("%s %s %s %s and %s are %s and %s, respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, NSLINEFWDSTATION,
                                NSLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
;                       output = String.format("%s %s %s %s and %s are %s and %s, respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, EWLINEFWDSTATION,
                                EWLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("%s %s %s %s and %s are %s and %s, respectively.",
                                stationCode, stationName, DELAYEDBOTHSUFFIX, CCLINEFWDSTATION,
                                CCLINEOPPSTATION, timeToNextStation, timeToNextStationOpp);
                    }
                    break;
                case 5:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                       output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                               stationName, DELAYEDONEWAYSUFFIX, NSLINEFWDSTATION, ONEWAYSUFFIX,
                               NSLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, EWLINEFWDSTATION, ONEWAYSUFFIX,
                                EWLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, CCLINEFWDSTATION, ONEWAYSUFFIX,
                                CCLINEFWDSTATION, timeToNextStation);
                    }
                    break;
                case 6:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, NSLINEOPPSTATION, ONEWAYSUFFIX,
                                NSLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, EWLINEOPPSTATION, ONEWAYSUFFIX,
                                EWLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("%s %s %s %s. %s %s is now %s.", stationCode,
                                stationName, DELAYEDONEWAYSUFFIX, CCLINEOPPSTATION, ONEWAYSUFFIX,
                                CCLINEOPPSTATION, timeToNextStationOpp);
                    }
                    break;
            }
        }

        return output;
    }
}