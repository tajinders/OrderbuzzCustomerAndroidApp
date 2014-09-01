package com.orderbuzz;
import java.util.ArrayList;

import com.orderbuzz.R;
import com.orderbuzz.cache.CartCache;
import com.orderbuzz.domain.OrderItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyCartListAdapter extends ArrayAdapter<OrderItem>{
	ArrayList <OrderItem> orderItemList;
	private LayoutInflater vi;
	Button totalpricebutton;
	TextView itemCount , Price; 

	public MyCartListAdapter(Context context, ArrayList<OrderItem> myCartList,  TextView itemCount, TextView Price) {
		super(context,0, myCartList);
		this.orderItemList = myCartList;
		this.itemCount = itemCount;
		this.Price = Price;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View itemView = convertView ;
		if(itemView == null) {
			itemView = vi.inflate(R.layout.cart_item_view, parent,false);
		}


		final OrderItem Cartobj = orderItemList.get(position);
		Button deletebutton = (Button) itemView.findViewById(R.id.delete);

		deletebutton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View arg0) {
				CartCache.getInstance().removeproduct(Cartobj.getRestid(), Cartobj.getProdid(), Cartobj.getSubprodid());

				if( CartCache.getInstance().getArrayList(Cartobj.getRestid()) !=null){
					Float totalprice=(float) 0;
					totalprice = CartCache.getInstance().calTotalPrice(Cartobj.getRestid());
					int itemcount = CartCache.getInstance().getArrayList(Cartobj.getRestid()).size();
					itemCount.setText(String.valueOf(itemcount));
					Price.setText(String.valueOf(totalprice));

				}
				notifyDataSetChanged();
			}

		});

		TextView makeText = (TextView)itemView.findViewById(R.id.ordersummary);
		makeText.setText(Cartobj.getOrderSummary());

		TextView subprodtext = (TextView)itemView.findViewById(R.id.SubProdName);
		subprodtext.setText(Cartobj.getSubprodname());

		TextView conditionText = (TextView)itemView.findViewById(R.id.OrderPrice);
		conditionText.setText("("+String.valueOf(Cartobj.getPrice())+"$)");
		return itemView;
	}


}
