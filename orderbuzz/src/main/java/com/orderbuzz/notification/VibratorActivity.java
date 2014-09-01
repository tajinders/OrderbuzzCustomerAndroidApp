package com.orderbuzz.notification;
import com.orderbuzz.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

public class VibratorActivity extends Activity
{
	private static Context context=null;
	private static Vibrator v;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrator_main);
		
		context = getApplicationContext();
		Bundle bdata = getIntent().getExtras();
		showAlert(VibratorActivity.this, bdata.getString("orderinfo")) ;
		
	}
	

		
	
	public static void showAlert(Activity activity, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message);
		builder.setCancelable(false);
	
		v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//v.vibrate(1000);
		long[] pattern = { 0, 200, 500 };
		v.vibrate(pattern, 0);
	
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				v.cancel();
				
//				Intent intent = new Intent( context , MenuOrderActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
				
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}