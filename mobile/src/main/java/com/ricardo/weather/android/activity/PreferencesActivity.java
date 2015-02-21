package com.ricardo.weather.android.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ricardo.weather.android.R;

public class PreferencesActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        initToolbar(getString(R.string.action_settings));

    }


    // Init

    protected void initToolbar(String title) {


        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();

        Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.view_toolbar, root, false);

        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_ToolbarTitle);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow));

        toolbar.setTitle(title);

        root.addView(toolbar, 0);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
            }

        });

    }


    // Fragments

    public static class PreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            //PreferenceManager.setDefaultValues(getActivity(), R.xml.advanced_preferences, false);

            // Load the preferences from an XML resource

            addPreferencesFromResource(R.xml.preferences);

        }
    }

}
