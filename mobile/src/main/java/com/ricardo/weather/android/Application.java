package com.ricardo.weather.android;

import com.ricardo.weather.android.client.Client;
import com.ricardo.weather.android.utility.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends android.app.Application {





    public void onCreate() {

        super.onCreate();

        init();

    }

    private void init() {

        Client.init();

        Utils.init(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getString(R.string.roboto_regular))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }

}
