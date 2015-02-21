package com.ricardo.weather.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ricardo.weather.android.R;

public abstract class BaseActivity extends ActionBarActivity {


    private final int SETTINGS_REQUEST = 10;

    private final String ROBOTO_TYPEFACE = "assets/RobotoTTF/";

    private final String ROBOTO_LIGHT = "Roboto-Light.ttf";
    private final String ROBOTO_MEDIUM = "Roboto-Medium.ttf";
    private final String ROBOTO_REGULAR = "Roboto-Regular.ttf";

    protected android.support.v7.widget.Toolbar toolbar;

    protected Typeface robotoLight;
    protected Typeface robotoMedium;
    protected Typeface robotoRegular;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        init();

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

        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Coming from Settings

        if (requestCode == SETTINGS_REQUEST && resultCode == RESULT_OK) {

            if(this instanceof MainActivity) {

                ((MainActivity) this).renderCondition();

                ((MainActivity) this).renderWindSpeed();

            }

        }

    }

    // Init

    private void init() {

        // Init Typefaces

        //initTypefaces();
    }


    protected void initTypefaces() {

        robotoLight = Typeface.createFromAsset(getAssets(), ROBOTO_TYPEFACE + ROBOTO_LIGHT);
        robotoMedium = Typeface.createFromAsset(getAssets(), ROBOTO_TYPEFACE + ROBOTO_MEDIUM);
        robotoRegular = Typeface.createFromAsset(getAssets(), ROBOTO_TYPEFACE + ROBOTO_REGULAR);

    }


    protected void initToolbar(String title) {

       toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

       toolbar.setTitleTextAppearance(this, R.style.TextAppearance_ToolbarTitle);

       toolbar.setTitle(title);

       if (toolbar != null) {

            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeAsUpIndicator(this.getResources().getDrawable(R.drawable.ic_drawer));

       }
    }



    // Start Activities

    private void startSettingsActivity() {

        Intent intent = new Intent(this, PreferencesActivity.class);

        startActivityForResult(intent, SETTINGS_REQUEST);

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

}
