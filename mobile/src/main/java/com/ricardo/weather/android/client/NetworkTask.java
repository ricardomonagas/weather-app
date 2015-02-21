package com.ricardo.weather.android.client;

import android.os.AsyncTask;
import android.util.Log;

import com.ricardo.weather.android.client.Client;
import com.ricardo.weather.android.utility.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class NetworkTask extends AsyncTask<String, Void, JSONObject> 
{

	
	String link;
	
	String method;
	
	String requestID;
	
	HttpGet getRequest;
	
	HttpPost postRequest;
	
	HttpDelete deleteRequest;
	
	public NetworkTask(String method, String requestID)
	{
		
		this.requestID = requestID; 
		
		this.method = method;
		
	}
	
	@Override
	protected JSONObject doInBackground(String... params) 
	{
		
		HttpUriRequest request = null;
		
		link = params[0];
		
		
		// Get Method
		
		if(method.contentEquals(Client.GET_METHOD))
		{
		
			getRequest = new HttpGet(link);
			
			request = getRequest;
			
		}
		
		// Post Method
		
		else if(method.contentEquals(Client.POST_METHOD))
		{
			
			postRequest = new HttpPost(link);
			
			request = postRequest;
			
			
		}
		
		
		// Delete Method
		
		else if(method.contentEquals(Client.DELETE_METHOD))
		{
			
			deleteRequest = new HttpDelete(link);
			
			request = deleteRequest;
		}
		
		
		try 
		{
			
			HttpResponse response = Client.getClient().execute(request);
			
			HttpEntity entity = readResponse(response);
			
			return readEntity(entity);
			
		} 
		catch (IOException e) 
		{
			

			e.printStackTrace();
			
		}
		
		return null;
	
	}
	
	protected HttpEntity readResponse(HttpResponse response)
	{
		
		HttpEntity entity = null;
		
		if(response != null)
		{
			
			Log.d(Utils.TAG, "Response != NULL");
			
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				
				Log.d(Utils.TAG, "It's not OK Status: " + response.getStatusLine().getStatusCode());
				
			}
			

			entity = response.getEntity();

		}
		else
		{
			
			Log.d(Utils.TAG, "Response = NULL");
			
		}
		
		
		return entity;
		
	}
	
	protected JSONObject readEntity(HttpEntity entity)
	{
		
		
		JSONObject result = null;
		
		if(entity != null)
		{
		
			try 
			{
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), HTTP.UTF_8));
				
				String line = null;
				
				do
				{	
					
					line = reader.readLine();
					
					
					if(line != null)
					
						result = new JSONObject(line);
					
				
					Log.d(Utils.TAG,"Line: " + line);
					
					
				}
				while(line != null);
			
			} 
			catch (UnsupportedEncodingException e) 
			{
				
				e.printStackTrace();
				
			} 
			catch (IllegalStateException e) 
			{
				

				e.printStackTrace();
				
			} 
			catch (IOException e) 
			{

				e.printStackTrace();
				
			} 
			catch (JSONException e) 
			{

				e.printStackTrace();
				
			}
			
			
		}

		return result;
		
	}

}
