package com.orderbuzz.rest;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.orderbuzz.cache.RestaurantMenuCache;
import com.orderbuzz.domain.Product;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.orderbuzz.MenuListViewActivity;
public class RestMenuGeneration  extends AsyncTask<String, Void, List<Product>>{
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		private ProgressDialog mProgressDialog;
		private Context context;

		public RestMenuGeneration(Context context) 
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
		protected List<Product> doInBackground(String... urls) {
			System.out.println(" DO Back ");
			//SystemClock.sleep(5000);
			String restId = urls[0];
			if (restId == null) 
				return null;

			String URL = "http://orderbuzz-orderbuzz.rhcloud.com/orderbuzz/restaurant/getrestmenu/"+restId;
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
			Product [] prodarray = restTemplate.getForObject(URL, Product[].class);
			if (prodarray.length == 0)
				return null;
			// converting Array to List
			List<Product> prodList = Arrays.asList(prodarray);
			RestaurantMenuCache.getInstance().AddRestaurantMenu(restId, prodList);
			return prodList;
		}


		@Override
		protected void onPostExecute(List<Product> prodList) {
			// TODO Auto-generated method stub
			super.onPostExecute(prodList);
			mProgressDialog.dismiss();
			((MenuListViewActivity)context).onTaskCompletedd(prodList);

		}

	}

