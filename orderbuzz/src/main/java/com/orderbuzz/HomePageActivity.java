package com.orderbuzz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;
import com.orderbuzz.R;
/*
 * This class has an image flipper and 2 buttons 
 * button1 - find nearby restaruants
 * button2 - find all restaurants limit by 10
 * 
 */
public class HomePageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);	
		Button nearby = (Button)findViewById(R.id.btn2);
		Button findall = (Button)findViewById(R.id.btn1);
		flipper.startFlipping();


		nearby.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePageActivity.this, RestaurantListViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		findall.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePageActivity.this, TopRestaurantListViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				
			}
		});
		
	}

}
