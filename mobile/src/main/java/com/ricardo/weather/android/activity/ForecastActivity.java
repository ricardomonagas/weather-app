package com.ricardo.weather.android.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.adapter.ForecastArrayAdapter;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class ForecastActivity extends BaseActivity {


    private ListView mList;
    private ForecastArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);

        init();

    }


    // Init

    private void init() {


        // Init Tool Bar

        initToolbar(getString(R.string.forecast));


        // Init Drawer

        initDrawer();


        // Init List

        initList();

    }


    public void initList() {

        String string = getIntent().getStringExtra(WeatherForecastAPI.WEATHER_KEY);

        if(string != null) {

            try {

                JSONArray array = new JSONArray(string);

                ArrayList<Object> arrayList = Utils.toArrayList(array);


                mList = (ListView) findViewById(R.id.list);

                mAdapter = new ForecastArrayAdapter(this, getApplicationContext(), arrayList);

                mList.setAdapter(mAdapter);


            } catch (JSONException exception) {


            }


        } else {

            renderInformationMessage(getString(R.string.no_forecast));

        }





    }

}
