package com.ricardo.weather.android.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.adapter.DrawerArrayAdapter;
import com.ricardo.weather.android.adapter.ForecastArrayAdapter;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class ForecastActivity extends ActionBarActivity {


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerLayout.DrawerListener mDrawerItemClickListener;

    private ListView mList;

    private ForecastArrayAdapter mAdapter;

    private JSONArray mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);

        init();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Init

    private void init() {

        // Init Drawer

        initDrawer();


        // Init List

        initList();

    }


    private void initDrawer()
    {


        String[] menuItems = getResources().getStringArray(R.array.menu_items);

        String[] menuIcons = getResources().getStringArray(R.array.menu_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new DrawerArrayAdapter(this, menuItems, menuIcons));

        // Set the list's click listener

        //mDrawerItemClickListener = new DrawerItemClickListener(this, mDrawerLayout);

        //mDrawerList.setOnItemClickListener(mDrawerItemClickListener);

        /*
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        )
        {

            public void onDrawerClosed(View view)
            {

                invalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView)
            {

                invalidateOptionsMenu();
            }
        };
        */

        //mDrawerLayout.setDrawerListener(drawerToggle);


        // Enable ActionBar app icon to behave as action to toggle nav drawer

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        //getActionBar().setHomeButtonEnabled(true);

    }

    private void initList() {

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
        }





    }

}
