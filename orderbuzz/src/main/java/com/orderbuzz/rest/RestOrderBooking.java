package com.orderbuzz.rest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.orderbuzz.rest.PaymentToken;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.orderbuzz.CartListViewActivity;
import com.orderbuzz.domain.Order;

public class RestOrderBooking extends AsyncTask<Order, Void, Boolean>{
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	private Context context;
	MyToken mytoken;

	public RestOrderBooking(Context context) 
	{
		this.context = context;
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("Please Wait..");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(true);
		mytoken = new PaymentToken();
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}

	////http://orderbuzz-orderbuzz.rhcloud.com/orderbuzz/order/submitorder?restid=1&status='pending'&summary='summary'&name='tim'&key='123'&gcmkey='rrrr'



	@Override
	protected Boolean doInBackground(Order... params) {
		System.out.println(" DO Back ");
		// Dont forget to clear your order cart ...... 
		System.out.println("MAH token " + params[0].getStripetokenno());
		Order order = params[0];
		if (order == null)
			return false;

		
		String URL = "http://orderbuzz-orderbuzz.rhcloud.com/orderbuzz/order/submitorder";
		//String URL = "http://192.168.2.18:8080/orderking/order/submitorder";
		//String URL = "http://postcatcher.in/catchers/53c1c1d23ada9602000003fc";
		//System.out.println(order.getStripetokenno());
		try {

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			// Add the Jackson and String message converters
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			Boolean Status = restTemplate.postForObject(URL, order, Boolean.class);
			
			if (!Status)
				return false;

			return true;

		}
		catch (Exception e){
			System.out.println(e.getMessage());
			return false ; 
		}

	}

	@Override
	protected void onPostExecute(Boolean Status) {
		// TODO Auto-generated method stub
		super.onPostExecute(Status);
		mProgressDialog.dismiss();
		((CartListViewActivity)context).onTaskCompleted(Status);

	}

}


