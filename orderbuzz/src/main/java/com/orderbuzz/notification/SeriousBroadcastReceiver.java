package com.orderbuzz.notification;

//package in.ultraneo.gcmexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class SeriousBroadcastReceiver extends BroadcastReceiver {

	private static String KEY = "c2dmPref";
	private static String REGISTRATION_KEY = "registrationKey";
	public static String regID = null;
	CharSequence tickerText;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
			String registrationId = intent.getStringExtra("registration_id");
			Log.i("uo", registrationId);
		} else if (intent.getAction().equals(
				"com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context, intent);
		}
	}

	private void handleMessage(Context context, Intent intent) {
		String a = intent.getAction();
		if (a.equals("com.google.android.c2dm.intent.RECEIVE")) {

		Toast.makeText(context, "GOTGCM PING", Toast.LENGTH_SHORT).show();		
			
			//			String payload = intent.getStringExtra("data");
			//			intent = new Intent(context, VibratorActivity.class);
			//			
			//			Bundle bundle = new Bundle();
			//			bundle.putString("orderinfo",payload );
			//			intent.putExtras(bundle);
			//			
			//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//			context.startActivity(intent);

		}
	}



	private void handleRegistration(Context context, Intent intent) {
		String registration = intent.getStringExtra("registration_id");
		if (intent.getStringExtra("error") != null) {

			Log.d("c2dm", "registration failed");
			String error = intent.getStringExtra("error");
			
			if (error == "SERVICE_NOT_AVAILABLE") {
				Log.d("c2dm", "SERVICE_NOT_AVAILABLE");
			} else if (error == "ACCOUNT_MISSING") {
				Log.d("c2dm", "ACCOUNT_MISSING");
			} else if (error == "AUTHENTICATION_FAILED") {
				Log.d("c2dm", "AUTHENTICATION_FAILED");
			} else if (error == "TOO_MANY_REGISTRATIONS") {
				Log.d("c2dm", "TOO_MANY_REGISTRATIONS");
			} else if (error == "INVALID_SENDER") {
				Log.d("c2dm", "INVALID_SENDER");
			} else if (error == "PHONE_REGISTRATION_ERROR") {
				Log.d("c2dm", "PHONE_REGISTRATION_ERROR");
			}

		
		} else if (intent.getStringExtra("unregistered") != null) {

			Log.d("c2dm", "unregistered");
			Toast.makeText(context, "C2DM unregistered sucessfully",
					Toast.LENGTH_SHORT).show();

		} else if (registration != null) {
			Log.d("c2dm", registration);
			Editor editor = context.getSharedPreferences(KEY,
					Context.MODE_PRIVATE).edit();
			editor.putString(REGISTRATION_KEY, registration);
			editor.commit();

			// Adding registeration id to register class object 
			regID = registration;
			RegisterGcm.getInstance().setRegisterationid(regID);
		}
	}

}
