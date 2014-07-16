package com.orderbuzz.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.orderbuzz.domain.Restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.orderbuzz.RestaurantListViewActivity;
import com.orderbuzz.TopRestaurantListViewActivity;

/**
 * 
 * @author Tajinder Singh
 * Making a call to rest api and will fetch list of restaurants object.  
 * 
 * Each object will have details like, rest id , name, address , queue no , and distance in kilometers
 * 
 * @Asyc We are making a rest call in doInBackground method. 
 * 
 * We have used spring rest template to consume restful webservices
 *  
 */


public class TopRestaurantInfo extends AsyncTask<String, Void, List<Restaurant>>{
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	private Context context;

	public TopRestaurantInfo(Context context) 
	{
		this.context = context;
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("Please Wait..");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(true);

	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}


	@Override
	protected List<Restaurant> doInBackground(String... urls) {
		String URL = "http://orderbuzz-orderbuzz.rhcloud.com/orderbuzz/restaurant/gettoprestinfo";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		Restaurant [] restarray = restTemplate.getForObject(URL, Restaurant[].class);
		if (restarray.length == 0)
			return null;
		// converting Array to List
		List<Restaurant> restList = Arrays.asList(restarray);
		return restList;
	}


	@Override
	protected void onPostExecute(List<Restaurant> restList) {
		// TODO Auto-generated method stub
		super.onPostExecute(restList);
		mProgressDialog.dismiss();
		((TopRestaurantListViewActivity)context).onTaskCompletedd(restList);

	}

}