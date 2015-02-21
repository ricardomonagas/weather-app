package com.ricardo.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.adapter.DrawerArrayAdapter;
import com.ricardo.weather.android.client.Client;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends BaseActivity {


    private LocationManager mLocationManager;
    private Location mCurrentLocation;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerLayout.DrawerListener mDrawerItemClickListener;

    private JSONObject mCity;
    private JSONObject mCurrentCondition;
    private JSONArray mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

    }

    // Init

    private void init() {


        // Init Tool Bar

        initToolbar(getString(R.string.today));


        // Show Loading

        showLoading();


        // Init Drawer

        initDrawer();


        // Get Current Location

        getLocation(this);

    }


    private void initDrawer()
    {


        String[] menuItems = getResources().getStringArray(R.array.menu_items);

        String[] menuIcons = getResources().getStringArray(R.array.menu_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new DrawerArrayAdapter(this, menuItems, menuIcons));

        // Set the list's click listener

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Drawer Item", "Clicked");

                startForecastActivity();

            }
        });

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


    // Load

    public void loadTodayData(JSONObject result) {

        JSONArray city = result.optJSONArray(WeatherForecastAPI.NEAREST_AREA_KEY);

        JSONArray array = result.optJSONArray(WeatherForecastAPI.CURRENT_CONDITION_KEY);

        if((array != null && array.length() > 0) && (city != null && city.length() > 0)) {


            mCity = city.optJSONObject(0);

            mCurrentCondition = array.optJSONObject(0);

            renderViews();

        }

    }


    public void loadForecastData(JSONObject result) {

        mForecast = result.optJSONArray(WeatherForecastAPI.WEATHER_KEY);

    }


    // Render

    private void renderViews() {


        renderConditionIcon();

        renderCity();

        renderCondition();

        renderHumidity();

        renderPrecipitation();

        renderPressure();

        renderWindSpeed();

        renderWeatherDirection();

        hideLoading();

    }


    private void renderConditionIcon() {

        JSONArray array = mCurrentCondition.optJSONArray(WeatherForecastAPI.WEATHER_ICON_URL_KEY);

        if(array != null && array.length() > 0) {

            try {

                JSONObject icon = (JSONObject) array.get(0);

                String url = icon.optString(WeatherForecastAPI.VALUE_KEY);

                Utils.displayImage(this, url, (ImageView) findViewById(R.id.condition_icon));

            } catch (JSONException exception) {}
        }

    }


    private void renderCity() {

        String regionText = "";

        String countryText = "";



        JSONArray regions = mCity.optJSONArray(WeatherForecastAPI.REGION_KEY);

        JSONArray countries = mCity.optJSONArray(WeatherForecastAPI.COUNTRY_KEY);


        // Region

        if (regions != null && regions.length() > 0) {

            try {

                JSONObject region = regions.getJSONObject(0);

                regionText = region.getString(WeatherForecastAPI.VALUE_KEY);

            } catch (JSONException ex) { }

        }

        // Country

        if (countries != null && countries.length() > 0) {

            try {

                JSONObject country = countries.getJSONObject(0);

                countryText = country.getString(WeatherForecastAPI.VALUE_KEY);

            } catch (JSONException ex) { }

        }


        TextView city = ((TextView) findViewById(R.id.city));

        city.setTypeface(robotoLight);

        city.setText(regionText + ", " + countryText);

    }


    public void renderCondition() {

        String key = getString(R.string.temperature_unit_key);

        String celsiusKey = getString(R.string.celsius_key);

        String fahrenheitKey = getString(R.string.fahrenheit_key);

        String conditionValue = "";

        String temperatureValue = "";

        String temperatureUnit = "";

        // Condition

        JSONArray conditions = mCurrentCondition.optJSONArray(WeatherForecastAPI.WEATHER_DESCRIPTION_KEY);

        if(conditions != null && conditions.length() > 0) {

            try {

                JSONObject condition = (JSONObject) conditions.get(0);

                conditionValue = condition.optString(WeatherForecastAPI.VALUE_KEY);

            } catch (JSONException exception) { }

        }


        // Temperature

        String currentUnit = Utils.getPreferenceValue(key);

        // Celsius

        if(currentUnit.contentEquals(celsiusKey)) {

            temperatureValue = mCurrentCondition.optString(WeatherForecastAPI.TEMP_C_KEY);

            temperatureUnit = getString(R.string.celsius_sign);
        }

        // Fahrenheit

        else if(currentUnit.contentEquals(fahrenheitKey)) {

            temperatureValue = mCurrentCondition.optString(WeatherForecastAPI.TEMP_F_KEY);

            temperatureUnit = getString(R.string.fahrenheit_sign);

        }


        // Render Text

        String message = !conditionValue.isEmpty() ? temperatureValue + temperatureUnit + " | " + conditionValue : temperatureValue;

        ((TextView) findViewById(R.id.temperature)).setText(message);

    }


    private void renderHumidity() {

        String value = mCurrentCondition.optString(WeatherForecastAPI.HUMIDITY_KEY);

        ((TextView) findViewById(R.id.humidity)).setText(value + "%");
    }


    private void renderPrecipitation() {

        String value = mCurrentCondition.optString(WeatherForecastAPI.PRECIPITATION_KEY);

        ((TextView) findViewById(R.id.precipitation)).setText(value + " mm");
    }


    private void renderPressure() {

        String value = mCurrentCondition.optString(WeatherForecastAPI.PRESSURE_KEY);

        ((TextView) findViewById(R.id.pressure)).setText(value + " hPa");
    }


    public void renderWindSpeed() {

        String key = getString(R.string.speed_unit_key);

        String kmhKey = getString(R.string.kilometers_ph_key);

        String mihKey = getString(R.string.miles_ph_key);

        String value = "";

        String unit = "";


        String currentUnit = Utils.getPreferenceValue(key);

        // Km/h

        if(currentUnit.contentEquals(kmhKey)) {

            value = mCurrentCondition.optString(WeatherForecastAPI.WIND_SPEED_KMPH_KEY);

            unit = getString(R.string.miles_ph_sign);
        }

        // Mi/h

        else if(currentUnit.contentEquals(mihKey)) {

            value = mCurrentCondition.optString(WeatherForecastAPI.WIND_SPEED_MIPH_KEY);

            unit = getString(R.string.miles_ph_sign);

        }

        ((TextView) findViewById(R.id.wind_speed)).setText(value + " " + unit);

    }


    private void renderWeatherDirection() {

        String value = mCurrentCondition.optString(WeatherForecastAPI.WEATHER_DIRECTION_KEY);

        ((TextView) findViewById(R.id.direction)).setText(value);
    }


    private void showLoading() {

        findViewById(R.id.loading).setVisibility(View.VISIBLE);

    }


    private void hideLoading() {

        findViewById(R.id.loading).setVisibility(View.GONE);

    }

    // Getters

    private void getLocation(Context context)
    {

        if(mLocationManager == null) {

            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        }


        // Network Provider

        if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

        } else if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        } else if (mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {

            mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, mLocationListener);

        } else {

            // Show a Dialog about GPS not activated

        }

    }


    // Other

    private void stopLocation() {

        if (mLocationManager != null) {

            mLocationManager.removeUpdates(mLocationListener);

        }

    }


    private void startForecastActivity() {

        Intent intent = new Intent(MainActivity.this, ForecastActivity.class);


        // Forecast

        if(mForecast != null && mForecast.length() > 0) {

            intent.putExtra(WeatherForecastAPI.WEATHER_KEY, mForecast.toString());

        }

        startActivity(intent);

    }


    // Listeners

    private LocationListener mLocationListener = new LocationListener()
    {

        public void onLocationChanged(Location location)
        {


            if(mCurrentLocation == null)
            {


                // Stop Location Listener

                stopLocation();

                // Get Current Location

                mCurrentLocation = location;

                float latitude = (float) mCurrentLocation.getLatitude();

                float longitude = (float) mCurrentLocation.getLongitude();


                Client.sendTodayWeatherRequest(MainActivity.this, latitude, longitude);

                Client.sendNextDaysWeatherRequest(MainActivity.this, latitude, longitude);

                // Save Location

                //Utils.getPreferences(Utils.TAG, getApplicationContext()).edit().putFloat(Media.LAT_KEY, latitude).apply();

                //Utils.getPreferences(Utils.TAG, getApplicationContext()).edit().putFloat(Media.LNG_KEY, longitude).apply();

            }

        }

    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void onProviderEnabled(String provider) {}

    public void onProviderDisabled(String provider) {}

};





}
