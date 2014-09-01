package com.orderbuzz;
import java.text.DecimalFormat;
import java.util.List;

import com.orderbuzz.R;
import com.orderbuzz.cache.RestaurantImageLoaderCache;
import com.orderbuzz.domain.Restaurant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopRestaurantListAdapter extends ArrayAdapter<Restaurant>{
	List <Restaurant> myResturants;
	private Context context;
	private LayoutInflater vi;

	public TopRestaurantListAdapter(Context context, List<Restaurant> myResturants ) {
		super(context,0, myResturants);
		this.myResturants = myResturants;
		this.context = context;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View itemView = convertView ;
		if(itemView == null)
		{
			System.out.println("OUTTIIIIT" + position);
			itemView = vi.inflate(R.layout.top_resturant_item_view, parent,false);

		}

		Restaurant Rest = myResturants.get(position);
		
		ImageView imageView = (ImageView)itemView.findViewById(R.id.restphoto);
		imageView.setImageBitmap(RestaurantImageLoaderCache.getInstance().getImage(Rest.getRest_Photo()));
		TextView name = (TextView)itemView.findViewById(R.id.restname);
		name.setText(Rest.getRest_Name());
		
		TextView queueno = (TextView)itemView.findViewById(R.id.restqueueno);
		queueno.setText(Rest.getRest_QueueNo());
		
		TextView address = (TextView)itemView.findViewById(R.id.restadd);
		address.setText(Rest.getAdd_desc());
		
		return itemView;
	}
}