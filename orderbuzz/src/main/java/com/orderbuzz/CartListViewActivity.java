package com.orderbuzz;
import java.util.ArrayList;
import com.orderbuzz.rest.MyToken;
import com.orderbuzz.rest.PaymentToken;
import com.orderbuzz.rest.RestOrderBooking;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.orderbuzz.R;
import com.orderbuzz.cache.CartCache;
import com.orderbuzz.cache.RestaurantImageLoaderCache;
import com.orderbuzz.domain.OrderItem;
import com.orderbuzz.domain.Order;
import com.orderbuzz.notification.RegisterGcm;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class CartListViewActivity extends Activity {

	ArrayList <OrderItem> orderItemList ;
	private String restaurantid, restUrl;
	Button  paynowbutton;
	String orderid;
	MyToken mytoken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_layout);
		Bundle data = getIntent().getExtras();
		restaurantid = data.getString("restid");
		restUrl = data.getString("restUrl");
		orderItemList = CartCache.getInstance().getArrayList(restaurantid);
		mytoken = new PaymentToken();
		populateListView();
	}


	private void populateListView() {
		// TODO Auto-generated method stub

		ListView list =(ListView) findViewById(R.id.CartListView);

		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );  
		LinearLayout ll = (LinearLayout)layoutInflater.inflate( R.layout.menu_header, null, false );
		ImageView imageView = (ImageView) ll.findViewById(R.id.restphotos);
		imageView.setImageBitmap(RestaurantImageLoaderCache.getInstance().getImage(restUrl));
		TextView itemCount = (TextView) ll.findViewById(R.id.itemcounttext);
		TextView  Price = (TextView) ll.findViewById(R.id.pricetext);

		if( CartCache.getInstance().getArrayList(restaurantid) !=null){
			Float totalprice=(float) 0;
			totalprice = CartCache.getInstance().calTotalPrice(restaurantid);
			int itemcount = CartCache.getInstance().getArrayList(restaurantid).size();
			itemCount.setText(String.valueOf(itemcount));
			Price.setText(String.valueOf(totalprice));
		}

		list.addHeaderView( ll );

		paynowbutton = new Button(this);
		paynowbutton.setText("PAY NOW");
		paynowbutton.setTextColor(Color.WHITE);
		paynowbutton.setBackgroundResource(R.drawable.bottomrowselected);
		list.addFooterView(paynowbutton);

		//		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//		final EditText userName = new EditText(this); 
		//		alert.setView(userName);
		//		
		//		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		//			public void onClick(DialogInterface dialog, int whichButton) {
		//				System.out.println("User "+  userName.getText().toString().trim());
		//			}
		//		});
		//
		//		alert.setNegativeButton("Cancel",
		//				new DialogInterface.OnClickListener() {
		//			public void onClick(DialogInterface dialog, int whichButton) {
		//				dialog.cancel();
		//			}
		//		});

		paynowbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(1);
			}
		});


		MyCartListAdapter adapter= new MyCartListAdapter(this,orderItemList, itemCount, Price);
		list.setAdapter(adapter);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder;
		switch(id) {
		case 1:
			builder = new AlertDialog.Builder(this);
			LinearLayout LLV= new LinearLayout(this);
			LLV.setOrientation(LinearLayout.VERTICAL);

			final EditText card = new EditText(this);
			card.setHint("4012888888881881");

			final ImageView Iv = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.height=300;			
			Iv.setBackgroundResource(R.drawable.paypal);
			Iv.setLayoutParams(lp);

			LinearLayout LLH= new LinearLayout(this);
			LLH.setOrientation(LinearLayout.HORIZONTAL);

			final EditText Month = new EditText(this);
			Month.setHint("11");

			final EditText Year = new EditText(this);
			Year.setHint("2017");

			final EditText Cvv = new EditText(this);
			Cvv.setHint("611");

			LLH.addView(Month);
			LLH.addView(Year);
			LLH.addView(Cvv);

			LLV.addView(Iv);	
			LLV.addView(card);
			LLV.addView(LLH);
			builder.setView(LLV);

			builder	.setCancelable(false)

			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					//					String cardno = card.getText().toString(); 
					//					Integer  month = Integer.parseInt(Month.getText().toString());
					//					Integer year = Integer.parseInt(Year.getText().toString()) ; 
					//					String cvv = Cvv.getText().toString(); 


					String cardno = "4012888888881881";
					Integer month = 11;
					Integer year = 2017; 
					String cvv = "611";
					final String secretKey = "234", gcmkey; 
					gcmkey = RegisterGcm.getInstance().getRegisterationid();
					if (gcmkey != null)
						MakeStripePayment(cardno , month , year , cvv,secretKey,gcmkey );
					else System.out.println("GCM null ");

				}
			})

			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//Do something here
				}
			});

			dialog = builder.create();
			break;   
		default:
			dialog = null;
		}
		return dialog;

	}



	private void MakeStripePayment (String cardno , Integer month , Integer year, String cvv , final String secretkey, final String  gcmkey){

		final String PUBLISHABLE_KEY = "pk_test_4Kzv9dqqhgQmLTBL6i7kKe9Y";
		Card card = new Card(cardno,month,year,cvv);
		boolean validation = card.validateCard();

		if (validation) {
			System.out.println("Validateeeee");
			new Stripe().createToken(
					card,
					PUBLISHABLE_KEY,
					new TokenCallback() {
						public void onSuccess(Token token) {

							
							Order order = new Order();
							order.setOrderGcmKey(gcmkey);
							order.setRestId(restaurantid);
							order.setOrderKey(secretkey);
							order.setStripetokenno(token.getId());
							order.setOrderItem(CartCache.getInstance().getArrayList(restaurantid));
							order.setTotalPrice(String.valueOf(CartCache.getInstance().calTotalPrice(restaurantid)));

							new RestOrderBooking(CartListViewActivity.this).execute(order);

						}

						public void onError(Exception error) {


						}
					});
		} else {

			return ; 
		}

	}


	public void onTaskCompleted(Boolean status) {
		// TODO Auto-generated method stub
		System.out.println(" called  ");

		if (status){
			CartCache.getInstance().empty(restaurantid);
			Intent intent = new Intent(this, RestaurantListViewActivity.class);
			startActivity(intent);
		}
		else 
		{
			System.out.println(" Transaction Fail ");
			// Transaction Fail , please try again 
		}

	}


}