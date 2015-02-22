package com.ricardo.weather.android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.adapter.DrawerArrayAdapter;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONArray;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends ActionBarActivity {


    private final int SETTINGS_REQUEST = 10;

    protected android.support.v7.widget.Toolbar mToolbar;

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected ListView mDrawerList;
    protected DrawerLayout.DrawerListener mDrawerItemClickListener;

    protected JSONArray mForecast;

    protected String mCurrentSpeedUnit;
    protected String mCurrentTemperatureUnit;

    protected Animation mAnimation;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Settings

        if (id == R.id.action_settings) {

            startSettingsActivity();

            return true;

        }

        // About

        else if(id == R.id.action_about) {

            startAboutDialog();

            return true;
        }

        // Drawer

        else {


            // Back Button

            if((id != R.id.action_more) && this instanceof PreferencesActivity)

                finish();

        }

        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Coming from Settings

        if (requestCode == SETTINGS_REQUEST) {


            if(this instanceof MainActivity) {


                String speedUnit = Utils.getPreferenceValue(getString(R.string.speed_unit_key), getString(R.string.kilometers_ph_key));

                String temperatureUnit = Utils.getPreferenceValue(getString(R.string.temperature_unit_key), getString(R.string.celsius_key));


                // Update Speed Unit

                if(!mCurrentSpeedUnit.contentEquals(speedUnit))

                    ((MainActivity) this).renderWindSpeed();


                // Update Temperature Unit

                if(!mCurrentTemperatureUnit.contentEquals(temperatureUnit))

                    ((MainActivity) this).renderCondition();


            } else if(this instanceof ForecastActivity){


                String temperatureUnit = Utils.getPreferenceValue(getString(R.string.temperature_unit_key), getString(R.string.celsius_key));

                // Update Temperature Unit

                if(!mCurrentTemperatureUnit.contentEquals(temperatureUnit)) {

                    ((ForecastActivity) this).initList();

                }

            }

        }

    }

    // Init

    protected void initToolbar(String title) {

       mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

       mToolbar.setTitleTextAppearance(this, R.style.TextAppearance_ToolbarTitle);

       mToolbar.setTitle(title);

       if (mToolbar != null) {

            setSupportActionBar(mToolbar);

            if(this instanceof PreferencesActivity) {

                getSupportActionBar().setHomeAsUpIndicator(this.getResources().getDrawable(R.drawable.ic_arrow));

            } else {

                getSupportActionBar().setHomeAsUpIndicator(this.getResources().getDrawable(R.drawable.ic_drawer));

            }
       }
    }


    protected void initDrawer()
    {


        String[] menuItems = getResources().getStringArray(R.array.menu_items);

        String[] menuIcons = getResources().getStringArray(R.array.menu_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new DrawerArrayAdapter(this, getApplicationContext(), menuItems, menuIcons));

        // Set the list's click listener

        mDrawerList.setOnItemClickListener(mDrawerOnItemClickListener);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.open,
                R.string.closed)
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

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }


    // Start Activities

    private void startSettingsActivity() {



        Intent intent = new Intent(this, PreferencesActivity.class);

        mCurrentSpeedUnit = Utils.getPreferenceValue(getString(R.string.speed_unit_key), getString(R.string.kilometers_ph_key));

        mCurrentTemperatureUnit = Utils.getPreferenceValue(getString(R.string.temperature_unit_key), getString(R.string.celsius_key));


        startActivityForResult(intent, SETTINGS_REQUEST);

    }


    protected void startMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }


    protected void startForecastActivity() {

        Intent intent = new Intent(this, ForecastActivity.class);

        // Forecast

        if(mForecast != null && mForecast.length() > 0) {

            intent.putExtra(WeatherForecastAPI.WEATHER_KEY, mForecast.toString());

        }

        startActivity(intent);

    }


    // Start Dialogs

    private void startAboutDialog() {

        new AlertDialog.Builder(this)
                       .setTitle(getString(R.string.about))
                       .setMessage(getString(R.string.about_text))
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }

                       }
        ).show();

    }


    // Render

    protected void renderInformationMessage(String message) {

        ViewGroup loading = (ViewGroup) findViewById(R.id.loading);


        // Stop Animation

        if(mAnimation != null)

            mAnimation.cancel();


        // Set Message

        TextView textView = (TextView) loading.findViewById(R.id.text);

        textView.setText(message);


        loading.setVisibility(View.VISIBLE);

    }

    // Show / Hide

    protected void showSunAnimation() {


        ViewGroup loading = (ViewGroup) findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);


        ImageView sun = (ImageView) loading.findViewById(R.id.sun);


        // Set Animation

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);

        sun.startAnimation(mAnimation);

    }

    protected void hideSunAnimation() {

        findViewById(R.id.loading).setVisibility(View.GONE);

        mAnimation.cancel();

    }



    // Listeners

    protected AdapterView.OnItemClickListener mDrawerOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            String currentActivityName = BaseActivity.this.getComponentName().getClassName();

            switch(position) {


                // Today

                case 0: {

                    String mainActivityName = MainActivity.class.getName();

                    if(!currentActivityName.contentEquals(mainActivityName))

                        startMainActivity();

                    break;
                }

                case 1: {

                    String forecastActivityName = ForecastActivity.class.getName();

                    if(!currentActivityName.contentEquals(forecastActivityName))

                        startForecastActivity();
                }

            }

        }
    };

}
