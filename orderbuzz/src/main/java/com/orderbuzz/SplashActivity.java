package com.orderbuzz;
import com.orderbuzz.rest.RestImageDownloader;
import com.orderbuzz.rest.RestImageDownloader.LoadingTaskFinishedListener;

import com.orderbuzz.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

	 
	public class SplashActivity extends Activity implements LoadingTaskFinishedListener {
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Show the splash screen
	        setContentView(R.layout.splash_screen);
	        // Find the progress bar
	       
	        ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
	        // Start your loading
	        new RestImageDownloader(progressBar, this, this).execute(); 
				
	    }
	 
	    // This is the callback for when your async task has finished
	    public void onTaskFinished() {
	        completeSplash();
	    }
	 
	    private void completeSplash(){
	        startApp();
	        finish(); // finish this Splash Activity so the user can't return to it!
	    }
	 
	    private void startApp() {
	    	
	    	
	        //Intent intent = new Intent(SplashActivity.this, RestaurantListViewActivity.class);
	        Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
			
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        startActivity(intent);
	        
	        
	    }
	}

