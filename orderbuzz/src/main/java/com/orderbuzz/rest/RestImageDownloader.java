package com.orderbuzz.rest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import com.orderbuzz.cache.RestaurantImageLoaderCache;
import com.orderbuzz.domain.Resources;
import android.os.AsyncTask;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ProgressBar;

/**
 * 
 * @author Tajinder Singh
 * Making a call to rest api and will fetch resources(photos) required for the application   
 * 
 * Each object will have details like, rest id, photourl
 * 
 * @Asyc We are making a rest call to download the image in doInBackground method. 
 * First time We download the image from URL and store it in application storage. 
 * Thereafter we find image in internal storage. This speeds up the loading process.    
 * 
 * We have used spring rest template to consume restful webservices. 
 * 
 * We are doing it at the time of splash screen
 *  
 */



public class RestImageDownloader extends AsyncTask<String, Integer, Integer> {


	public interface LoadingTaskFinishedListener {
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

	// This is the progress bar you want to update while the task is in progress
	private final ProgressBar progressBar;
	// This is the listener that will be told when this task is finished
	private final LoadingTaskFinishedListener finishedListener;
	private Context context;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public RestImageDownloader(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener , Context context) {
		this.progressBar = progressBar;
		this.finishedListener = finishedListener;
		this.context = context;
	}



	private String DownloadFromUrl(String fileName, String urlStr) throws IOException
	{
		File fileWithinMyDir = context.getFilesDir();  

		URL url = new URL(urlStr);
		File file = new File( fileWithinMyDir.getAbsolutePath()  + "/"+"thumbnail" +fileName+".png");
		URLConnection ucon = url.openConnection();
		InputStream is = ucon.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		while ((current = bis.read()) != -1) 
		{
			baf.append((byte) current);
		}

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(baf.toByteArray());
		fos.close();

		fileWithinMyDir = context.getFilesDir();
		Bitmap bitmap = BitmapFactory.decodeFile(fileWithinMyDir.getAbsolutePath() + "/" +"thumbnail"+fileName+".png");
		RestaurantImageLoaderCache.getInstance().setImageLoaderList(urlStr, bitmap);

		return "";
	}


	// Here you would query your app's internal state to see if this download had been performed before
	// Perhaps once checked save this in a shared preference for speed of access next time
	// returning true so we show the splash every time

	@SuppressLint("NewApi") 
	private boolean resourcesDontAlreadyExist(String fileName) {

		File fileWithinMyDir = context.getFilesDir();

		//		if(RestaurantImageLoaderCache.getInstance().getImageLoaderList().get(fileWithinMyDir.getAbsolutePath() + "/" +"thumbnail"+fileName+".png") == null)
		//		return false;
		//		else return true;

		Bitmap bitmap = BitmapFactory.decodeFile(fileWithinMyDir.getAbsolutePath()  + "/" +"thumbnail"+fileName+".png");        

		if(bitmap == null ) 
		{
			System.out.println("_B_"); return true;
		}
		else 
			return false ;
	}


	@Override
	protected Integer doInBackground(String... params) {

		String URL = "http://orderbuzz-orderbuzz.rhcloud.com/orderbuzz/restaurant/getappresources";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		Resources [] resourcesarray = restTemplate.getForObject(URL, Resources[].class);

		if (resourcesarray.length == 0)
			return null;

		int progresscounter = 0 ;
		for ( Resources myresource : resourcesarray ) {


			int progress = (int) ((progresscounter / (float) resourcesarray.length) * 100);
			progresscounter ++;
			publishProgress(progress);

			//			if(resourcesDontAlreadyExist(myresource.getRest_id_pk())){
			try {
				if(RestaurantImageLoaderCache.getInstance().getImageLoaderList().get(myresource.getRest_photo()) == null)
				{					DownloadFromUrl(myresource.getRest_id_pk(),myresource.getRest_photo());

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 1234;

		//					// We are just imitating some process thats takes a bit of time (loading of resources / downloading)
		//						int counter = 10;
		//						for (int i = 0; i < counter; i++) {
		//				
		//							// Update the progress bar after every step
		//							int progress = (int) ((i / (float) counter) * 100);
		//							publishProgress(progress);
		//				
		//							// Do some long loading things
		//							try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
		//						}
		//						return counter;

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
	}
}


