package com.orderbuzz.cache;
import java.util.HashMap;
import android.graphics.Bitmap;

public class RestaurantImageLoaderCache {
		private HashMap<String,Bitmap> imageLoaderList;
		private static RestaurantImageLoaderCache instance;

		private RestaurantImageLoaderCache(){
			imageLoaderList = new HashMap<String,Bitmap>();
		}

		public static RestaurantImageLoaderCache getInstance(){
			if (instance == null){
				instance = new RestaurantImageLoaderCache();
			}
			return instance;
		}

		public Bitmap getImage(String key) {
			return imageLoaderList.get(key);
		}

		public HashMap<String, Bitmap> getImageLoaderList() {
			return imageLoaderList;
		}

		public void setImageLoaderList(String url, Bitmap bitmap) {
			getImageLoaderList().put(url, bitmap);
		}

}
