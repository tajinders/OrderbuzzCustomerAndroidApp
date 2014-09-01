package com.orderbuzz.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.orderbuzz.domain.OrderItem;

public class CartCache {

	private Map <String,ArrayList<OrderItem>> resturantcart;
	private static CartCache instance;

	private CartCache(){
		resturantcart = new HashMap <String,ArrayList<OrderItem>>();
	}

	public static CartCache getInstance(){
		if (instance == null){
			instance = new CartCache();
		}
		return instance;
	}

	public void setArrayList(String resturantid, OrderItem cartobject) {
		if(getArrayList(resturantid) !=null){
		getArrayList(resturantid).add(cartobject);
		}else{
			ArrayList<OrderItem> cartarray = new ArrayList<OrderItem>();
			cartarray.add(cartobject);
			resturantcart.put(resturantid, cartarray);
		}
		
	}

	public ArrayList<OrderItem> getArrayList(String resturantid) {
		return resturantcart.get(resturantid);
	}

	public void removeproduct(String resturantid ,String prodid, String subprodid){

		for(int i=0;i<resturantcart.get(resturantid).size();i++)
		{
			String cartsubprodid = resturantcart.get(resturantid).get(i).getSubprodid();
			String cartprodid = resturantcart.get(resturantid).get(i).getProdid();

			if (subprodid == cartsubprodid && cartprodid ==prodid)
			{
				//removing the object from cart
				resturantcart.get(resturantid).remove(i);
				//Setting total price 
				break;
			}
		}

	}
	public float calTotalPrice(String resturantid)
	{
		float totalprice=0;
		for(int i=0;i<resturantcart.get(resturantid).size();i++)
		{
			totalprice = totalprice + resturantcart.get(resturantid).get(i).getPrice();
		}
		return totalprice;
	}
	
	public void empty(String resturantid)
	{
		resturantcart.remove(resturantid);
		
		
	}
	
}

