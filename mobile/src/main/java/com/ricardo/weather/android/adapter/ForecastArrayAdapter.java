package com.ricardo.weather.android.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardo.weather.android.R;
import com.ricardo.weather.android.utility.Utils;
import com.ricardo.weather.android.utility.WeatherForecastAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastArrayAdapter extends ArrayAdapter<Object>
{


    // Constants


    // Variables

    protected Activity activity;

	protected Context context;

    protected ArrayList<Object> values;


    // Classes

    static class ViewHolder
    {



        public ImageView icon;

	    public TextView title;

	}


	// Constructor

	public ForecastArrayAdapter(Activity activity, Context context, ArrayList<Object> values)
	{
	    
		super(context, R.layout.weather_list_item, values);

        this.activity = activity;

	    this.context = context;

        this.values = values;
	    
	 }
	
	
	// Methods

    @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

        View rowView;

        if(convertView != null) {

            rowView = convertView;

        } else {

            LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();

            rowView = inflater.inflate(R.layout.weather_list_item, parent, false);

        }

        JSONObject item = (JSONObject) values.get(position);

        renderView(rowView, item);


	    return rowView;
	    
	 }

    private void renderView(View view, JSONObject item) {

        JSONArray array = null;

        try {

            array = item.getJSONArray(WeatherForecastAPI.HOURLY_KEY);

            if(array != null && array.length() > 0) {

                JSONObject value = (JSONObject) array.get(0);


                // Icon

                JSONArray icons = value.getJSONArray(WeatherForecastAPI.WEATHER_ICON_URL_KEY);

                if(icons != null && icons.length() > 0) {

                    JSONObject icon = (JSONObject) icons.get(0);

                    String url = icon.getString(WeatherForecastAPI.VALUE_KEY);

                    Utils.displayImage(context, url, (ImageView) view.findViewById(R.id.icon));

                }

                // Week Day

                String date = item.getString(WeatherForecastAPI.DATE_VALUE_KEY);

                String day = getWeekDay(date);

                ((TextView) view.findViewById(R.id.day)).setText(day);


                // Temperature

                String temperature = value.optString(WeatherForecastAPI.TEMPC_KEY);

                ((TextView) view.findViewById(R.id.temperature)).setText(temperature + "°C");


                // Weather Description / Condition

                JSONArray descriptions = value.getJSONArray(WeatherForecastAPI.WEATHER_DESCRIPTION_KEY);

                if(descriptions != null && descriptions.length() > 0) {

                    JSONObject description = (JSONObject) descriptions.get(0);

                    String text = description.getString(WeatherForecastAPI.VALUE_KEY);

                    ((TextView) view.findViewById(R.id.condition)).setText(text);

                }


            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }
	 
    private String getWeekDay(String stringDate) {

        String result = null;

        try {

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = dateFormat.parse(stringDate);

            calendar.setTime(date);

            result = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        } catch (ParseException ex) { }

        return result;
    }

}
