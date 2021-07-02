package com.example.coronatracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    final String url = "https://api.covid19api.com/summary";
    private StringRequest stringRequest;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.appBar);
        setSupportActionBar(myToolbar);
        Button countryButton = findViewById(R.id.selectCountry);
        Button worldButton = findViewById(R.id.selectWorld);
        Log.i("Creation", "I've been created.");
        final Intent countryIntent = new Intent(this, CountryMenuActivity.class);
        final Intent worldIntent = new Intent(this, WorldStatsActivity.class);

        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(countryIntent);
            }
        });


        worldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(worldIntent);
            }
        });
    }

    /**
     * Connects to the api and sends a get request. Receives a string response that needs to be parsed to a JsonObject.
     * @param country the country to be analyzed
     */
    public void getData(final String country) {
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);

                CountryInfo countryInfo = new CountryInfo(convertedObject);
                countryInfo.extractInfo(country);
                TextView numDeaths = findViewById(R.id.deathNumbers);
                numDeaths.setText(countryInfo.getNumDeaths());
                TextView numRecovered = findViewById(R.id.recoveryNumbers);
                numRecovered.setText(countryInfo.getNumRecovered());
                TextView numCases = findViewById(R.id.casesNumbers);
                numCases.setText(countryInfo.getNumCases());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work.");
            }
        });
        requestQueue.add(stringRequest);
    }




}