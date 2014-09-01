package com.orderbuzz;
import java.util.List;

import com.orderbuzz.R;
import com.orderbuzz.cache.CartCache;
import com.orderbuzz.cache.RestaurantImageLoaderCache;
import com.orderbuzz.cache.RestaurantMenuCache;
import com.orderbuzz.domain.OrderItem;
import com.orderbuzz.domain.ChildProductMainOptions;
import com.orderbuzz.domain.ChildProductSubOptions;
import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MenuOptionListViewActivity extends ExpandableListActivity
{
	//Initialize variables
	private int ParentClickStatus=-1;
	private int ChildClickStatus=-1;
	private List<ChildProductMainOptions> cprodmainList;
	float Currentprice;
	String restID,restUrl;
	TextView Price;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Resources res = this.getResources();
		Drawable devider = res.getDrawable(R.drawable.line);
		Bundle data = getIntent().getExtras();

		final int parentID = data.getInt("parentid");
		final int childID =  data.getInt("childid");
		this.restID = data.getString("restID");
		this.restUrl = data.getString("restUrl");

		this.Currentprice = RestaurantMenuCache.getInstance().getRestMenu(restID).get(parentID).getChildProdList().get(childID).getChildProdBasePrice();
		this.cprodmainList = RestaurantMenuCache.getInstance().getRestMenu(restID).get(parentID).getChildProdList().get(childID).getChildProductMainOptionsList();

		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );  
		LinearLayout ll = (LinearLayout)layoutInflater.inflate( R.layout.menu_header, null, false );
		ImageView imageView = (ImageView) ll.findViewById(R.id.restphotos);
		imageView.setImageBitmap(RestaurantImageLoaderCache.getInstance().getImage(restUrl));

		TextView itemCount = (TextView) ll.findViewById(R.id.itemcounttext);
		ImageView itemView = (ImageView) ll.findViewById(R.id.itemcount);
		itemCount.setVisibility(ll.INVISIBLE);
		itemView.setVisibility(ll.INVISIBLE);



		Price = (TextView) ll.findViewById(R.id.pricetext);
		Price.setText(String.valueOf(Currentprice));
		getExpandableListView( ).addHeaderView( ll );

		final Button addtocart = new Button(this);
		addtocart.setText("ADD TO CART");
		addtocart.setTextColor(Color.WHITE);
		addtocart.setBackgroundResource(R.drawable.bottomrowselected);
		getExpandableListView().addFooterView(addtocart);

		// Set ExpandableListView values
		getExpandableListView().setGroupIndicator(null);
		getExpandableListView().setDivider(devider);
		getExpandableListView().setChildDivider(devider);
		getExpandableListView().setDividerHeight(1);
		registerForContextMenu(getExpandableListView());



		//Setting price and prod main options

		addtocart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click   
				String prodName = RestaurantMenuCache.getInstance().getRestMenu(restID).get(parentID).getProdname();
				String subprodName = RestaurantMenuCache.getInstance().getRestMenu(restID).get(parentID).getChildProdList().get(childID).getChildProdName();
				String prodUrl =  RestaurantMenuCache.getInstance().getRestMenu(restID).get(parentID).getProdPhoto();
				String subProdId = String.valueOf(childID);
				String prodId = String.valueOf(parentID);
				OrderItem oc_item = new OrderItem( restID , prodId, subProdId, prodName, subprodName,prodUrl,Currentprice, cprodmainList );
				CartCache.getInstance().setArrayList(restID,oc_item);

				Intent intent = new Intent(getApplicationContext(), MenuListViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("restID", restID);
				bundle.putString("restUrl",restUrl);
				intent.putExtras(bundle);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);

			}
		});


		if (this.getExpandableListAdapter() == null)
		{
			//Create ExpandableListAdapter Object
			final MenuOptionListViewAdapter mAdapter = new MenuOptionListViewAdapter();

			// Set Adapter to ExpandableList Adapter
			this.setListAdapter(mAdapter);
		}
		else
		{
			// Refresh ExpandableListView data 
			((MenuOptionListViewAdapter)getExpandableListAdapter()).notifyDataSetChanged();
		}	

	}


	/**
	 * A Custom adapter to create Parent view (Used grouprow.xml) and Child View((Used childrow.xml).
	 */
	private class MenuOptionListViewAdapter extends BaseExpandableListAdapter
	{

		private LayoutInflater inflater;

		public MenuOptionListViewAdapter()
		{
			// Create Layout Inflator
			inflater = LayoutInflater.from(MenuOptionListViewActivity.this);
		}


		// This Function used to inflate parent rows view



		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return cprodmainList.get(groupPosition).getChildProductSubOptionsList().get(childPosition);
		}


		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			/****** When Child row clicked then this function call *******/
			//			
			Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
			if( ChildClickStatus!=childPosition)
			{
				ChildClickStatus = childPosition;

				Toast.makeText(getApplicationContext(), "Parent :"+groupPosition + " Child :"+childPosition , 
						Toast.LENGTH_LONG).show();
			}  
			//			
			return childPosition;
		}


		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			int size=0;
			if(cprodmainList.get(groupPosition).getChildProductSubOptionsList()!=null)
				size = cprodmainList.get(groupPosition).getChildProductSubOptionsList().size();
			return size;
		}


		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return cprodmainList.get(groupPosition);
		}


		public int getGroupCount() {
			// TODO Auto-generated method stub
			return cprodmainList.size();

		}


		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub

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

		@Override
		public void notifyDataSetChanged()
		{
			// Refresh List rows
			super.notifyDataSetChanged();
		}



		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;		}


		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			System.out.println("_ child click _");
			return true;
		}


		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parentView) {
			// TODO Auto-generated method stub
			final ChildProductMainOptions cprodmain = cprodmainList.get(groupPosition);
			final ChildProductSubOptions cprodsub = cprodmain.getChildProductSubOptionsList().get(childPosition);

			// Inflate childrow.xml file for child rows
			convertView = inflater.inflate(R.layout.menu_option_childrow, parentView, false);

			// Get childrow.xml file elements and set values
			((TextView) convertView.findViewById(R.id.text1)).setText(cprodsub.getChildProdSubOptionName());
			((TextView) convertView.findViewById(R.id.text)).setText(String.valueOf(cprodsub.getChildProdSubOptionPrice()));


			CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);

			if(cprodmain.isSingleSelection())
				checkbox.setButtonDrawable(R.drawable.radiobutton_selector);
			else
				checkbox.setButtonDrawable(R.drawable.checkbox_selector);

			checkbox.setChecked(cprodsub.isChecked());
			checkbox.setOnCheckedChangeListener(new CheckUpdateListener(cprodsub,cprodmain,cprodmain.isSingleSelection()));
			return convertView;
		}



		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parentView) {
			final ChildProductMainOptions cprodmain = cprodmainList.get(groupPosition);

			// Inflate grouprow.xml file for parent rows
			convertView = inflater.inflate(R.layout.menu_option_grouprow, parentView, false); 

			// Get grouprow.xml file elements and set values
			((TextView) convertView.findViewById(R.id.text1)).setText(cprodmain.getChildProdMainOptionName());

			ImageView rightcheck=(ImageView)convertView.findViewById(R.id.rightcheck);
			if (isExpanded == true)
				rightcheck.setImageResource(getResources().getIdentifier("App.OrderBuzz:drawable/group_down_arrow",null,null));
			else
				rightcheck.setImageResource(getResources().getIdentifier("App.OrderBuzz:drawable/group_up_arrow",null,null));

			return convertView;

		}

	

		
		private final class CheckUpdateListener implements OnCheckedChangeListener
		{
			private  ChildProductMainOptions mainopt =null;
			private  ChildProductSubOptions subopt=null;
			private boolean selectiontype;
			private CheckUpdateListener(ChildProductSubOptions subopt, ChildProductMainOptions mainopt, boolean selectiontype)
			{
				this.subopt=subopt;
				this.mainopt = mainopt;
				this.selectiontype = selectiontype;
			}

			private CheckUpdateListener(ChildProductMainOptions mainopt)
			{
				this.mainopt = mainopt;
			}


			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(selectiontype == true)
				{
					for(int i=0;i<mainopt.getChildProductSubOptionsList().size();i++)
					{
						if (mainopt.getChildProductSubOptionsList().get(i).isChecked() == true)
						{
							mainopt.getChildProductSubOptionsList().get(i).setChecked(false);
							Currentprice = Currentprice - mainopt.getChildProductSubOptionsList().get(i).getChildProdSubOptionPrice();
						}
					}

					subopt.setChecked(true);
					notifyDataSetChanged();
					Currentprice = Currentprice + subopt.getChildProdSubOptionPrice();
				}		

				else{
					if (subopt!=null){
						subopt.setChecked(isChecked);
						//productoptions.setChecked(isChecked);
						Log.i("Child click is ", "isChecked: "+ isChecked);
						System.out.println("ParentID" + mainopt.getChildProdMainOptionName());
						System.out.println("Child ID" + subopt.getChildProdSubOptionName());
						if ( subopt.isChecked() == true)
						{
							Currentprice = Currentprice + subopt.getChildProdSubOptionPrice();

						}else {

							Currentprice = Currentprice - subopt.getChildProdSubOptionPrice();
						}	
					}
				}
				System.out.println("Price is " + Currentprice);
				Price.setText(String.valueOf(String.valueOf(Currentprice)));
				//	priceTagButton.setText(String.valueOf(Currentprice));
			}
		}
	}
}
