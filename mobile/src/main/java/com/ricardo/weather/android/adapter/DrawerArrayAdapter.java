package com.ricardo.weather.android.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardo.weather.android.R;

public class DrawerArrayAdapter extends ArrayAdapter<Object>
{


    // Constants

    public static final String TODAY_KEY = "today";

    public static final String FORECAST_KEY = "forecast";


    // Variables

	protected Context context;

    protected String[] icons;

    protected String[] values;


    // Classes

    static class ViewHolder
    {



        public ImageView icon;

	    public TextView title;

	}


	// Constructor

	public DrawerArrayAdapter(Context context, String[] values, String[] icons)
	{
	    
		super(context, R.layout.icon_list_item, values);
	    
	    this.context = context;
	    
	    this.values = values;

        this.icons = icons;
	    
	 }
	
	
	// Methods

    @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		 
	    View rowView = convertView;

	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	
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

			String caption = values[position];
		    
		    if(caption != null)
		    {

		        holder.title.setText(caption);
		    	
		    }
		    else
		    	
		    	holder.title.setText("");

	}

    private void loadIcon(ViewHolder holder, int position)
    {

        // Today

        if(icons[position].contentEquals(TODAY_KEY))

            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drawer_today_dark));

        // Forecast

        else if(icons[position].contentEquals(FORECAST_KEY))

            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drawer_forecast_dark));

    }

}
