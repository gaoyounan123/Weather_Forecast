package com.example.gaoyounan.weather_forecast;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gaoyounan.weather_forecast.model.Weather_Info;

import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    static final String URL = "http://api.openweathermap.org/data/2.5/weather?APPID=662f377c5940b30cca84d1a9b7a5ce9f&q=";
    static final String SYMBOL = "\u2103";

    private EditText editTextView;
    private TextView textView_temperature;
    private TextView textView_min_max;
    private TextView textView_main_weather;
    private TextView textView_description;
    private TextView textView_humidity;
    private TextView textView_city_name;
    private TextView textView_cloud_coverage;
    private Button btn_get_weather;

    private String city_name;

    private Runnable runnable;

    private Integer K_to_C(double k)
    {
        return (int)(k - 273.15);
    }


    public void getWeatherInformation(){

        String urlWithBase = URL.concat(TextUtils.isEmpty(city_name) ? "HALIFAX": city_name);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlWithBase, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                        try{

                            textView_city_name.setText(response.getString("name"));
                            textView_main_weather.setText(response.getJSONArray("weather").getJSONObject(0).getString("main"));
                            textView_description.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                            textView_humidity.setText(response.getJSONObject("main").getString("humidity") + "%\nHumidity");
                            textView_cloud_coverage.setText(response.getJSONObject("clouds").getString("all") + "%\nClouds");
                            //textView_min_max.setText
                            Double temp_min = response.getJSONObject("main").getDouble("temp_min");
                            Double temp_max = response.getJSONObject("main").getDouble("temp_max");
                            textView_min_max.setText("Min. " + String.valueOf(K_to_C(temp_min)) + SYMBOL + " Max." + String.valueOf(K_to_C(temp_max))  + SYMBOL);

                            Double temp = response.getJSONObject("main").getDouble("temp");
                            textView_temperature.setText(String.valueOf(K_to_C(temp)) + SYMBOL);

                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(getApplicationContext(), "Error retrieving Data", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueueSignleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextView = findViewById(R.id.etEnterCity);
        textView_temperature = findViewById(R.id.temperature);
        textView_min_max = findViewById(R.id.min_mum);
        textView_main_weather = findViewById(R.id.weather_main);
        textView_description = findViewById(R.id.weather_description);
        textView_humidity = findViewById(R.id.humidity);
        textView_cloud_coverage = findViewById(R.id.cloud_coverage);
        btn_get_weather = findViewById(R.id.btnSearch);
        textView_city_name = findViewById(R.id.cityName_id);

        getWeatherInformation();


        btn_get_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_name = editTextView.getText().toString();

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getWeatherInformation();
                    }
                };

                Thread thread = new Thread(null, runnable, "background");
                thread.start();

                editTextView.clearFocus();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextView.getWindowToken(),0);

            }
        });


    }
}
