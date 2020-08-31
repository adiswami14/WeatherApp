package com.example.lalliswami.realweatherapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText zipCode;
    TextView currentDisp;
    Button weatherClick;
    JSONObject data;
    JSONArray list;
    ArrayList<Integer> temp_min;
    ArrayList<Integer> temp_max;
    ArrayList<String> quotes;
    ArrayList<String> cornyquotes;
    ImageView imager, image1, image2, image3, image4;
    int temp;
    TextView quote;
    TextView corn;
    TextView time0;
    TextView high1;
    TextView high2;
    TextView high3;
    TextView high4;
    TextView low1;
    TextView low2;
    TextView low3;
    TextView low4;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    String weather;
    String str1,str2, str3, str4;
    long dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipCode = findViewById(R.id.id_zipcode);
        currentDisp = findViewById(R.id.id_currtemp);
        weatherClick = findViewById(R.id.id_getweather);
        quote = findViewById(R.id.id_quote);
        corn = findViewById(R.id.id_cornything);
        time0 = findViewById(R.id.id_currtime);
        high1 = findViewById(R.id.id_high1);
        high2 = findViewById(R.id.id_high2);
        high3 = findViewById(R.id.id_high3);
        high4 = findViewById(R.id.id_high4);
        low1 = findViewById(R.id.id_low1);
        low2 = findViewById(R.id.id_low2);
        low3 = findViewById(R.id.id_low3);
        low4 = findViewById(R.id.id_low4);
        time1 = findViewById(R.id.id_time1);
        time2 = findViewById(R.id.id_time2);
        time3= findViewById(R.id.id_time3);
        time4= findViewById(R.id.id_time4);
        imager= findViewById(R.id.id_image);
        image1= findViewById(R.id.id_image1);
        image2= findViewById(R.id.id_image2);
        image3= findViewById(R.id.id_image3);
        image4= findViewById(R.id.id_image4);
        temp_min = new ArrayList<>();
        temp_max = new ArrayList<>();
        quotes = new ArrayList<>();
        cornyquotes=new ArrayList<>();
        temp =0;
        quotesFill();
        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String str = s +"";
                weatherClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Async().execute(str+"");
                        temp_max.clear();
                        temp_min.clear();
                        dt = 0;
                    }
                });
            }
        });



    }
   public void quotesFill()
    {
        quotes.add("Stay ready to ball.");
        quotes.add("Be confident, and take your shot.");
        quotes.add("Defense wins rings.");
        quotes.add("You are MORE than just an athlete.");
        quotes.add("All that matters is the WIN.");
        quotes.add("Remember, in the end, it's just a game.");
        quotes.add("You will always bounce back from a bad game.");
        quotes.add("It's always a good day to play ball.");
        quotes.add("Keep your eyes up, and follow through.");
        quotes.add("Never lose the passion for the game.");

        cornyquotes.add("More daylight than Lebron sees while driving");
        cornyquotes.add("Clouds are few, like the amount of championships the Cavs have");
        cornyquotes.add("Clouds are scattered, just like the crowds at a Bulls game");
        cornyquotes.add("Clouds are more broken than the hearts of a Kings fan");
        cornyquotes.add("Rains are showery and coming in quick spurts, like the Rockets' offense");
        cornyquotes.add("Wetter outside than Steph Curry's jumper");
        cornyquotes.add("More thunderous than Westbrook's dunks");
        cornyquotes.add("Snowier and colder than Lonzo Ball's jumpshot");
        cornyquotes.add("The eyes of every Lakers fan was misty when Kobe retired, just like the weather");

    }
    public class Async extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            URL URLurl;
            String url="";
            URLConnection connection;
            InputStream stream;
            BufferedReader reader;

            url = "http://api.openweathermap.org/data/2.5/forecast?zip="+strings[0]+"&APPID=248ce9eab3ed5258e1c1e04213add1e8&units=imperial";
            Log.d("TAG", url);
           // url = "http://192.241.169.168/data/2.5/weather?zip="+strings[0]+"&appid=248ce9eab3ed5258e1c1e04213add1e8";

            String string = "";
            try {
                    URLurl = new URL(url);
                    connection = URLurl.openConnection();
                    connection.connect();
                    stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    string = reader.readLine();
                    return string;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
                try {
                    data = new JSONObject(string);
                    list = data.getJSONArray("list");
                    dt=0;
                    weather = list.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
                    str1 = list.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
                    str2 = list.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main");
                    str3 = list.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main");
                    str4 = list.getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main");
                    for (int x = 0; x < 5; x++) {
                        JSONObject w = list.getJSONObject(x).getJSONObject("main");
                        if(x==0) {
                            temp = (int) (Math.round(w.getDouble("temp")));
                            dt = list.getJSONObject(x).getLong("dt");


                        }
                        else {
                            temp_min.add((int) Math.round(w.getDouble("temp_min")));
                            temp_max.add((int) Math.round(w.getDouble("temp_max")));
                        }
                    }
                    SetUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
    public void SetUp() {
        currentDisp.setText(temp + " F");
        /*time0.setText(timeConvert(Long.toString(dt)));
        time1.setText(addThreeHours(time0.getText()+""));
        time2.setText(addThreeHours((time1.getText())+""));
        time3.setText(addThreeHours(time2.getText()+""));
        time4.setText(addThreeHours(time3.getText()+""));*/

        setAllImagesPlusCornyQuotes();
        quote.setText(quotes.get((int) (Math.random() * quotes.size())));
        for (int x = 0; x < 4; x++) {
            int t_h = temp_max.get(x);
            int t_l = temp_min.get(x);
            switch (x) {
                case 0:
                    high1.setText("High:" + t_h + " F");
                    low1.setText("Low:" + t_l + " F");
                    break;
                case 1:
                    high2.setText("High:" + t_h + " F");
                    low2.setText("Low:" + t_l + " F");
                    break;
                case 2:
                    high3.setText("High:" + t_h + " F");
                    low3.setText("Low:" + t_l + " F");
                    break;
                case 3:
                    high4.setText("High:" + t_h + " F");
                    low4.setText("Low:" + t_l + " F");
                    break;
                default:
                    break;
            }
        }
    }
     public void setAllImagesPlusCornyQuotes()
     {
         int i = 0;
         switch(weather)
         {
             case "Clear":
                 corn.setText(cornyquotes.get(0));
                 imager.setImageResource(R.drawable.clearday);
                 break;
             case "Clouds":
                 corn.setText(cornyquotes.get(1));
                 imager.setImageResource(R.drawable.fewcloudsday);
                 break;
             case "Rain":
                 corn.setText(cornyquotes.get(5));
                 imager.setImageResource(R.drawable.rain);
                 break;
             case "Thunderstorm":
                 corn.setText(cornyquotes.get(6));
                 imager.setImageResource(R.drawable.thunderday);
                 break;
             case "Snow":
                 corn.setText(cornyquotes.get(7));
                 imager.setImageResource(R.drawable.snowday);
                 break;
             case "Mist":
                 corn.setText(cornyquotes.get(8));
                 imager.setImageResource(R.drawable.mist);
                 break;
             default:break;
         }
         switch(str1)
         {
             case "Clear":
                 image1.setImageResource(R.drawable.clearday);
                 break;
             case "Clouds":
                 image1.setImageResource(R.drawable.fewcloudsday);
                 break;
             case "Rain":
                 image1.setImageResource(R.drawable.rain);
                 break;
             case "Thunderstorm":
                 image1.setImageResource(R.drawable.thunderday);
                 break;
             case "Snow":
                 image1.setImageResource(R.drawable.snowday);
                 break;
             case "Mist":
                 image1.setImageResource(R.drawable.mist);
                 break;
             default:break;
         }
         switch(str2)
         {
             case "Clear":
                 image2.setImageResource(R.drawable.clearday);
                 break;
             case "Clouds":
                 image2.setImageResource(R.drawable.fewcloudsday);
                 break;
             case "Rain":
                 image2.setImageResource(R.drawable.rain);
                 break;
             case "Thunderstorm":
                 image2.setImageResource(R.drawable.thunderday);
                 break;
             case "Snow":
                 image2.setImageResource(R.drawable.snowday);
                 break;
             case "Mist":
                 image2.setImageResource(R.drawable.mist);
                 break;
             default:break;
         }
         switch(str3)
         {
             case "Clear":
                 image3.setImageResource(R.drawable.clearday);
                 break;
             case "Clouds":
                 image3.setImageResource(R.drawable.fewcloudsday);
                 break;
             case "Rain":
                 image3.setImageResource(R.drawable.rain);
                 break;
             case "Thunderstorm":
                 image3.setImageResource(R.drawable.thunderday);
                 break;
             case "Snow":
                 image3.setImageResource(R.drawable.snowday);
                 break;
             case "Mist":
                 image3.setImageResource(R.drawable.mist);
                 break;
             default:break;
         }
         switch(str4)
         {
             case "Clear":
                 image4.setImageResource(R.drawable.clearday);
                 break;
             case "Clouds":
                 image4.setImageResource(R.drawable.fewcloudsday);
                 break;
             case "Rain":
                 image4.setImageResource(R.drawable.rain);
                 break;
             case "Thunderstorm":
                 image4.setImageResource(R.drawable.thunderday);
                 break;
             case "Snow":
                 image4.setImageResource(R.drawable.snowday);
                 break;
             case "Mist":
                 image4.setImageResource(R.drawable.mist);
                 break;
             default:break;
         }
         time0.setText("12 PM");
         time1.setText("3 PM");
         time2.setText("6 PM");
         time3.setText("9 PM");
         time4.setText("12 AM");
     }
    public String timeConvert(String s)
    {
        String str = s.substring(0,2);
        int hour = Integer.parseInt(str);
        hour-=5;
        if(hour<=0)
            hour+=24;

        else if(hour>24)
            hour-=24;
        if(hour<12)
        {
            return hour+":00";
        }
        else if (hour>12)
        {
            return hour+":00";
        }
        else return hour+":00";
    }
    public String addThreeHours(String s)
    {
        String str = "";
        for(int x=0;x<s.length();x++)
        {
            if(s.charAt(x) == ' ')
                str = s.substring(0,x);
        }
        int hour = Integer.parseInt(str);
        hour+=3;
        if(hour>24)
            hour-=24;
        if(hour<12 )
        {
            return hour+":00";
        }
        else if (hour>12)
        {
            return hour+":00";
        }
        else return hour+":00";
    }
}
