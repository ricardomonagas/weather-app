package com.ricardo.weather.android.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.utility.Utils;

public class PreferencesActivity extends BaseActivity {


    protected PreferencesFragment mPreferencesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        init();
    }


    private void init() {


        // Init Tool Bar

        initToolbar(getString(R.string.action_settings));


        // Add Preferences Fragment

        mPreferencesFragment = new PreferencesFragment();

        getFragmentManager().beginTransaction().replace(R.id.content_frame, mPreferencesFragment).commit();


    }


    // Fragments

    public static class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener  {

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource

            addPreferencesFromResource(R.xml.preferences);

            // Init Summaries

            initSummaries();

        }


        @Override
        public void onResume() {

            super.onResume();

            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }


        @Override
        public void onPause() {

            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

            super.onPause();
        }


        // Init

        private void initSummaries() {


            ListPreference speedUnit = (ListPreference) findPreference(getString(R.string.speed_unit_key));

            ListPreference temperatureUnit = (ListPreference) findPreference(getString(R.string.temperature_unit_key));


            // Speed

            String value = Utils.getPreferenceValue(getString(R.string.speed_unit_key), getString(R.string.kilometers_ph_key));

            if(value.contentEquals(getString(R.string.kilometers_ph_key))) {

                speedUnit.setSummary(getString(R.string.kilometers_ph));

            } else if(value.contentEquals(getString(R.string.miles_ph_key))) {

                speedUnit.setSummary(getString(R.string.miles_ph));
            }


            // Temperature

            value = Utils.getPreferenceValue(getString(R.string.temperature_unit_key), getString(R.string.celsius_key));

            if(value.contentEquals(getString(R.string.celsius_key))) {


                temperatureUnit.setSummary(getString(R.string.celsius));


            } else if(value.contentEquals(getString(R.string.fahrenheit_key))) {

                temperatureUnit.setSummary(getString(R.string.fahrenheit));

            }


        }

        // Listeners

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            Preference preference = findPreference(key);

            if (preference instanceof ListPreference) {

                ListPreference listPreference = (ListPreference) preference;

                preference.setSummary(listPreference.getEntry());

            }
        }

    }

}
