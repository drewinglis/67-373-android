package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.GetDetailsTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieShowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_show);
		
		Bundle info = getIntent().getExtras();
		try {
			int id = info.getInt("id");
			String json = new GetDetailsTask().execute(id).get();
			JSONObject movie = new JSONObject(json).getJSONObject("movie");
			
			TextView item = (TextView)findViewById(R.id.title);
			item.setText(movie.getString("name"));
			
			item = (TextView)findViewById(R.id.year);
			item.setText(movie.getString("release_date"));
			
			item = (TextView)findViewById(R.id.critic_value);
			item.setText(movie.getString("critic_rating"));
			
			item = (TextView)findViewById(R.id.audience_value);
			item.setText(movie.getString("audience_rating"));
			
			new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute(movie.getString("poster_picture_url"));
			
			item = (TextView)findViewById(R.id.synopsis);
			item.setText(movie.getString("synopsis"));
			
			item = (TextView)findViewById(R.id.mpaa_rating);
			item.setText(movie.getString("mpaa_rating"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.Home:
	      		Intent mainIntent = new Intent(this, MainActivity.class);
	    		startActivity(mainIntent);;
	            return true;
	        case R.id.Search:
	      		Intent searchIntent = new Intent(this, SearchActivity.class);
	    		startActivity(searchIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
}
