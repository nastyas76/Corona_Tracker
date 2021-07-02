package com.example.coronatracker;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CountryStatsActivity extends MainActivity {
    private String country;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrystats);
        Bundle extras = getIntent().getExtras();
        country = extras.getString("countryName");
        getData(country);
        TextView view = findViewById(R.id.countryName);
        view.setText(country
        );
    }

    public void getData(String country) {
        super.getData(country);
    }
}
