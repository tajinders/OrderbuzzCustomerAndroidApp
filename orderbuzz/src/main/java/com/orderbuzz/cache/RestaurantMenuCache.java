package com.orderbuzz.cache;
import java.util.HashMap;
import java.util.List;
import com.orderbuzz.domain.Product;

/*
 * Making this class as Singleton and it will store restaurant menu in Hash Map
 */

public class RestaurantMenuCache {
	private HashMap<String,List<Product>> restMenuCacheMap;
	private static RestaurantMenuCache instance;

	private RestaurantMenuCache(){
		restMenuCacheMap = new HashMap<String,List<Product>>();
	}

	public static RestaurantMenuCache getInstance(){
		if (instance == null){
			instance = new RestaurantMenuCache();
		}
		return instance;
	}

	public List<Product> getRestMenu(String restId) {
		return restMenuCacheMap.get(restId);
	}

	public void AddRestaurantMenu(String key , List<Product> productList)
	{
		restMenuCacheMap.put(key, productList);
	}
}
