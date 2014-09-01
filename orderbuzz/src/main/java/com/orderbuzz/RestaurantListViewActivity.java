package com.orderbuzz;
import java.util.List;
import com.orderbuzz.rest.NearbyRestaurantInfo;
import com.orderbuzz.R;
import com.orderbuzz.domain.Restaurant;
import com.orderbuzz.location.GPSTracker;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
/*
 * This Activity will display list of restaurants 
 * Key restaurant elements : Name , Queue No , Address , Distance in KM
 */
public class RestaurantListViewActivity extends Activity {

	private ListView myListView;
	List<Restaurant> restList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new LongOperation().execute();
		setContentView(R.layout.restaurant_layout);
		myListView = (ListView)findViewById(R.id.OCListView);

		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(), MenuListViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("restID", restList.get(position).getRest_id_pk());
				bundle.putString("restUrl", restList.get(position).getRest_Photo());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		System.out.println(" on resume ");

		GPSTracker gps = new GPSTracker(RestaurantListViewActivity.this);
		if(gps.canGetLocation()){

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			new NearbyRestaurantInfo(this).execute("5400",String.valueOf(latitude) ,String.valueOf(longitude));
			Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	

		}else{
			gps.showSettingsAlert();
		}
	}

	/**
	 * Thia method will get result of Async Call to rest api
	 * @param restList
	 */

	public void RedirectionAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("No Nearby Restaurants Found");

		// Setting Dialog Message
		alertDialog.setMessage("We are redirecting you to Top Restaurants");

		// On pressing Settings button
		alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				Intent intent = new Intent(RestaurantListViewActivity.this, TopRestaurantListViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}


	public void onTaskCompletedd(List<Restaurant> restList) {
		// TODO Auto-generated method stub
	
		if (restList != null){
			this.restList = restList;
			MyRestaurantListAdapter adapter= new MyRestaurantListAdapter(this,restList);
			myListView.setAdapter(adapter);

		}else{
			RedirectionAlert();
		}
		//Update UI
	}

}
