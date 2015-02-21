package com.ricardo.weather.android.client;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import com.ricardo.weather.android.activity.MainActivity;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONObject;

public class Client 
{
	
	
	// General

		// Parameters
	
		public static final String CLIENT_ID_KEY = "client_id";
		
		public static final String CLIENT_SECRET_KEY = "client_secret";
		

		// Methods
		
		public static final String GET_METHOD = "GET";
		
		public static final String POST_METHOD = "POST";
		
		public static final String DELETE_METHOD = "DEL";
		
	
	// Variables
		
	public static AndroidHttpClient httpClient;
	
	
	// Init
	
	public static void init()
	{
		
	    getClient();
		
	}


    // Other

    private static boolean isGoodResponse(JSONObject response) {

        boolean result = false;

        if(response.optJSONObject(WeatherForecastAPI.DATA_KEY) != null) {

            result = true;

        }

        return result;
    }


    // Requests

    public static void sendRequest(final Context context, String method, String url, String requestID)
    {

        new NetworkTask(method, requestID)
        {

            @Override
            protected void onPostExecute(JSONObject response)
            {

                if(response != null) {

                    if(isGoodResponse(response)) {

                        JSONObject data = response.optJSONObject(WeatherForecastAPI.DATA_KEY);

                        // Today Forecast

                        if(requestID.contentEquals(WeatherForecastAPI.TODAY_WEATHER_REQUEST_KEY)) {

                            if (context instanceof MainActivity) {

                                ((MainActivity) context).loadTodayData(data);

                            }

                        }

                        // Next Days Forecast

                        else {

                            if (context instanceof MainActivity) {

                                ((MainActivity) context).loadForecastData(data);

                            }

                        }

                    }

                }

            }

        }.execute(url);

    }

    public static void sendTodayWeatherRequest(final Context context, float latitude, float longitude) {

        String url = WeatherForecastAPI.createTodayWeatherURL(latitude, longitude);

        sendRequest(context, Utils.GET_METHOD, url, WeatherForecastAPI.TODAY_WEATHER_REQUEST_KEY);

    }

    public static void sendNextDaysWeatherRequest(final Context context, float latitude, float longitude) {

        String url = WeatherForecastAPI.createNextDaysWeatherURL(latitude, longitude);

        sendRequest(context, Utils.GET_METHOD, url, WeatherForecastAPI.NEXT_DAYS_WEATHER_REQUEST_KEY);

    }

    private static void parseData(Context context, Object data, String requestID) {

        if (data != null) {

            JSONObject element = (JSONObject) data;


        }

    }


    // Getters

    public static AndroidHttpClient getClient()
    {

        if(httpClient == null)

            httpClient = AndroidHttpClient.newInstance("WeatherForecast");


        return httpClient;

    }
	

}
