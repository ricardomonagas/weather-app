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
import com.ricardo.weather.android.activity.ForecastActivity;
import com.ricardo.weather.android.activity.MainActivity;

public class DrawerArrayAdapter extends ArrayAdapter<Object>
{


    // Constants

    public static final String TODAY_KEY = "today";

    public static final String FORECAST_KEY = "forecast";


    // Variables

    protected Activity mActivity;

	protected Context mContext;

    protected String[] mIcons;

    protected String[] mValues;


    // Classes

    static class ViewHolder
    {



        public ImageView icon;

	    public TextView title;

	}


	// Constructor

	public DrawerArrayAdapter(Activity activity, Context context, String[] values, String[] icons)
	{
	    
		super(context, R.layout.icon_list_item, values);

        this.mActivity = activity;
	    
	    this.mContext = context;
	    
	    this.mValues = values;

        this.mIcons = icons;
	    
	 }
	
	
	// Methods

    @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		 
	    View rowView = convertView;

	    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	
	    rowView = inflater.inflate(R.layout.icon_list_item, null);
	    	

	    // Configure View Holder
	    	
	    ViewHolder viewHolder = new ViewHolder();

	    	
	    viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
	    	
	    viewHolder.title = (TextView) rowView.findViewById(R.id.title);


        // Set Background

        //rowView.setBackgroundResource(R.drawable.menu_item);


	    // Fill Data

		loadViews(viewHolder, position);

	    return rowView;
	    
	 }
	 
	private void loadViews(ViewHolder holder, int position)
	{



            // Icon

            loadIcon(holder, position);


            // Title

			loadTitle(holder, position);

	}

    private void loadTitle(ViewHolder holder, int position) {


        String caption = mValues[position];

        if(caption != null)
        {

            holder.title.setText(caption);

            if((mActivity instanceof MainActivity) && mIcons[position].contentEquals(TODAY_KEY))

                holder.title.setTextColor(mActivity.getResources().getColor(R.color.action_bar_color));

            else if((mActivity instanceof ForecastActivity) && mIcons[position].contentEquals(FORECAST_KEY))

                holder.title.setTextColor(mActivity.getResources().getColor(R.color.action_bar_color));



        }
        else

            holder.title.setText("");
    }

    private void loadIcon(ViewHolder holder, int position)
    {

        // Today

        if(mIcons[position].contentEquals(TODAY_KEY))

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_drawer_today_dark));

        // Forecast

        else if(mIcons[position].contentEquals(FORECAST_KEY))

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_drawer_forecast_dark));

    }

}
