package com.orderbuzz;
import java.util.List;

import com.orderbuzz.rest.RestMenuGeneration;

import com.orderbuzz.R;
import com.orderbuzz.cache.CartCache;
import com.orderbuzz.cache.RestaurantImageLoaderCache;
import com.orderbuzz.domain.ChildProduct;
import com.orderbuzz.domain.Product;
import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListViewActivity  extends  ExpandableListActivity
{
	//Initialize variables
	private int ParentClickStatus=-1;
	private int ChildClickStatus=-1;
	private List<Product> prodList;
	private ExpandableListView myview;
	String restID,restUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.menu_header);

		Bundle data = getIntent().getExtras();
		this.restID = data.getString("restID");
		restUrl =  data.getString("restUrl");


		// Set ExpandableListView values
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );  
		LinearLayout ll = (LinearLayout)layoutInflater.inflate( R.layout.menu_header, null, false );
		ImageView imageView = (ImageView) ll.findViewById(R.id.restphotos);
		imageView.setImageBitmap(RestaurantImageLoaderCache.getInstance().getImage(restUrl));
		TextView itemCount = (TextView) ll.findViewById(R.id.itemcounttext);
		TextView Price = (TextView) ll.findViewById(R.id.pricetext);

		if( CartCache.getInstance().getArrayList(restID) !=null){
			Float totalprice=(float) 0;
			totalprice = CartCache.getInstance().calTotalPrice(restID);
			int itemcount = CartCache.getInstance().getArrayList(restID).size();
			itemCount.setText(String.valueOf(itemcount));
			Price.setText(String.valueOf(totalprice));
		}

		getExpandableListView( ).addHeaderView( ll );

		final Button reviewandpaybutton = new Button(this);
		reviewandpaybutton.setText("REVIEW & PAY");
		reviewandpaybutton.setTextColor(Color.WHITE);
		reviewandpaybutton.setBackgroundResource(R.drawable.bottomrowselected);
		getExpandableListView().addFooterView(reviewandpaybutton);

		Resources res = this.getResources();
		Drawable devider = res.getDrawable(R.drawable.line);
		getExpandableListView().setGroupIndicator(null);
		getExpandableListView().setDivider(devider);
		getExpandableListView().setChildDivider(devider);
		getExpandableListView().setDividerHeight(1);
		getExpandableListView().setBackgroundColor(Color.GRAY);

		registerForContextMenu(getExpandableListView());
		myview = getExpandableListView();

		// Activity Parameters 
		reviewandpaybutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if( CartCache.getInstance().getArrayList(restID) !=null){

					/* GCM Registeration */
					GcmRegisteration(v);
					
					Intent intent = new Intent(getApplicationContext(), CartListViewActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("restid", restID);
					bundle.putString("restUrl", restUrl);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}

		});

		new RestMenuGeneration(this).execute(restID);

		myview.setOnChildClickListener(new OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				int parentID = groupPosition;
				int childID = childPosition;
				Intent intent = new Intent(getApplicationContext(), MenuOptionListViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("parentid", parentID);
				bundle.putInt("childid", childID);
				bundle.putString("restID", restID);
				bundle.putString("restUrl", restUrl);
				intent.putExtras(bundle);
				startActivity(intent);
				return true;

			}
		});

	}

	
	private void GcmRegisteration(View v )
	{
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(v.getContext(), 0, new Intent(), 0));
		registrationIntent.putExtra("sender","836555334633");// this 
		v.getContext().startService(registrationIntent);
		
	}
	
	public void setAdapter()
	{
		if (this.getExpandableListAdapter() == null) {
			//Create ExpandableListAdapter Object
			final MenuListViewAdapter mAdapter = new MenuListViewAdapter();

			// Set Adapter to ExpandableList Adapter
			this.setListAdapter(mAdapter);
		}
		else {
			// Refresh ExpandableListView data 
			((MenuListViewAdapter)getExpandableListAdapter()).notifyDataSetChanged();
		}	
	}

	private class MenuListViewAdapter extends BaseExpandableListAdapter
	{

		private LayoutInflater inflater;
		public MenuListViewAdapter() {
			// Create Layout Inflator
			inflater = LayoutInflater.from(MenuListViewActivity.this);
		}


		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parentView) {
			// TODO Auto-generated method stub
			final Product product = prodList.get(groupPosition);
			final ChildProduct childProduct = product.getChildProdList().get(childPosition);

			// Inflate childrow.xml file for child rows
			convertView = inflater.inflate(R.layout.menu_childrow, parentView, false);

			// Get childrow.xml file elements and set values
			((TextView) convertView.findViewById(R.id.text1)).setText(childProduct.getChildProdName());
			((TextView) convertView.findViewById(R.id.text)).setText(String.valueOf(childProduct.getChildProdBasePrice()));

			ImageView image=(ImageView)convertView.findViewById(R.id.image);
			image.setImageResource(getResources().getIdentifier("com.androidexample.customexpandablelist:drawable/setting"+product.getProdname(),null,null));
			return convertView;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parentView) {
			final Product product = prodList.get(groupPosition);

			// Inflate grouprow.xml file for parent rows
			convertView = inflater.inflate(R.layout.menu_grouprow, parentView, false); 

			// Get grouprow.xml file elements and set values
			((TextView) convertView.findViewById(R.id.text1)).setText(product.getProdname());
			((TextView) convertView.findViewById(R.id.text)).setText(product.getProdPhoto());

			ImageView rightcheck=(ImageView)convertView.findViewById(R.id.rightcheck);
			if (isExpanded == true)
				rightcheck.setImageResource(getResources().getIdentifier("App.OrderBuzz:drawable/group_down_arrow",null,null));
			else
				rightcheck.setImageResource(getResources().getIdentifier("App.OrderBuzz:drawable/group_up_arrow",null,null));

			return convertView;
		}


		// This Function used to inflate parent rows view
		public Object getChild(int groupPosition, int childPosition) {
			return prodList.get(groupPosition).getChildProdList().get(childPosition);
		}


		public long getChildId(int groupPosition, int childPosition) {
			// When Child row clicked then this function call 
			Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
			if( ChildClickStatus!=childPosition)
			{
				ChildClickStatus = childPosition;
				Toast.makeText(getApplicationContext(), "Parent :"+groupPosition + " Child :"+childPosition , 
						Toast.LENGTH_LONG).show();
			}  
			return childPosition;
		}


		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			int size=0;
			if(prodList.get(groupPosition).getChildProdList()!=null)
				size = prodList.get(groupPosition).getChildProdList().size();
			return size;
		}


		public Object getGroup(int groupPosition) {
			return prodList.get(groupPosition);
		}


		public int getGroupCount() {
			return prodList.size();
		}


		public long getGroupId(int groupPosition) {
			Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);
			if(groupPosition==2 && ParentClickStatus!=groupPosition){
				//Alert to user
				Toast.makeText(getApplicationContext(), "Parent :"+groupPosition , 
						Toast.LENGTH_LONG).show();
			}

			ParentClickStatus=groupPosition;
			if(ParentClickStatus==0)
				ParentClickStatus=-1;

			return groupPosition;
		}

		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	public void onTaskCompletedd(List<Product> prodList) {
		if (prodList != null)
		{
			this.prodList =  prodList;
			setAdapter();
		}
	}
}
