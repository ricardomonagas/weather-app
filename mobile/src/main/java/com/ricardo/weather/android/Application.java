package com.ricardo.weather.android;

import com.ricardo.weather.android.client.Client;
import com.ricardo.weather.android.utility.Utils;

public class Application extends android.app.Application {





    public void onCreate() {

        super.onCreate();

        init();

    }

    private void init() {

        Client.init();

        Utils.init(getApplicationContext());

    }

}
