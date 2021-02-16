package com.example.mrt4you_mobile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsActivity extends BaseActivity
{
    private static final String NSLINEFWDSTATION = "Jurong East";
    private static final String NSLINEOPPSTATION = "Marina South Pier";
    private static final String EWLINEFWDSTATION = "Pasir Ris";
    private static final String EWLINEOPPSTATION = "Tuas Link";
    private static final String CCLINEFWDSTATION = "Dhoby Ghaut";
    private static final String CCLINEOPPSTATION = "HarbourFront";

    protected static final String NSPREFIX = "ns";
    protected static final String EWPREFIX = "ew";
    protected static final String CCPREFIX = "cc";

    private static final String AZURENEWSAPIURL = "https://mrt4youweb.azurewebsites.net/api/news";
    private static final String LOCALNEWSAPIURL = "http://10.0.2.2:63414/api/News";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        updateView();
        ImageButton refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(v-> updateView());
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

    public void updateView()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() ->
        {

            List<News> newsList = new ArrayList<>();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");

            //Background work here
            try
            {
                URL url = new URL(AZURENEWSAPIURL);
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
                    News news = new News();
                    news.setTime(LocalDateTime.now().format(df));
                    news.setMsg(getString(R.string.no_news));

                    newsList.add(news);
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

                        String stationCode = jsonObject.get("stationCode").toString().trim();
                        String stationName = jsonObject.get("stationName").toString().trim();
                        int timeToNextStation = Integer.parseInt(jsonObject.get("timeToNextStation")
                                .toString());
                        int timeToNextStationOpp = Integer.parseInt(jsonObject
                                .get("timeToNextStationOpp").toString());
                        int currentStatus = Integer.parseInt(jsonObject.get("currentStatus").toString());

                        News news = new News();
                        news.setTime(time.format(df));
                        news.setMsg(generateNewsMsg(stationCode, stationName, timeToNextStation, timeToNextStationOpp, currentStatus));
                        news.setImageBasedOnStationCode(stationCode);

                        newsList.add(news);
                    }
                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
                TextView textConnectionFailed = findViewById(R.id.textConnectionFailed);
                ListView listView = findViewById(R.id.listView);
                runOnUiThread(() -> {
                    textConnectionFailed.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                });
            }

            News[] newsArr = new News[newsList.size()];
            newsList.toArray(newsArr);

            handler.post(() ->
            {
                //UI Thread work here
                NewsDisplayAdapter newsDisplayAdapter = new NewsDisplayAdapter(this, 0);
                newsDisplayAdapter.setData(newsArr);

                ListView listView = findViewById(R.id.listView);
                if (listView != null)
                {
                    listView.setAdapter(newsDisplayAdapter);
                }

            });
        });
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
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEFWDSTATION, ONEWAYSUFFIX,
                                EWLINEOPPSTATION, timeToNextStationOpp);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, CCLINEFWDSTATION, ONEWAYSUFFIX,
                                CCLINEOPPSTATION, timeToNextStationOpp);
                    }
                    break;
                case 3:
                    if (stationCode.toLowerCase().startsWith(NSPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, NSLINEOPPSTATION, ONEWAYSUFFIX,
                                NSLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(EWPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
                                stationName, BREAKDOWNONEWAYSUFFIX, EWLINEOPPSTATION, ONEWAYSUFFIX,
                                EWLINEFWDSTATION, timeToNextStation);
                    }
                    else if (stationCode.toLowerCase().startsWith(CCPREFIX))
                    {
                        output = String.format("[%s] %s %s %s. %s %s is %s minute(s).", stationCode,
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

    static class News
    {
        private String time, msg;
        private int image;

        public void setImageBasedOnStationCode(String stationCode)
        {
            String line = stationCode.substring(0, 2).toLowerCase();
            switch (line)
            {
                case NSPREFIX:
                    image = R.drawable.ns_label;
                    break;
                case EWPREFIX:
                    image = R.drawable.ew_label;
                    break;
                case CCPREFIX:
                    image = R.drawable.cc_label;
                    break;
            }
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getImage() {
            return image;
        }
    }

}