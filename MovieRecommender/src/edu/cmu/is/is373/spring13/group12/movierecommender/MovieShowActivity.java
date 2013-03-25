package edu.cmu.is.is373.spring13.group12.movierecommender;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MovieShowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_show);
		
		Bundle info = getIntent().getExtras();
		try {
			JSONObject movie = new JSONObject(info.getString("json"));
			
			TextView item = (TextView)findViewById(R.id.title);
			item.setText(movie.getString("name"));
			
			item = (TextView)findViewById(R.id.year);
			item.setText(movie.getString("release_date"));
			
			item = (TextView)findViewById(R.id.critic_value);
			item.setText(movie.getString("critic_rating"));
			
			item = (TextView)findViewById(R.id.audience_value);
			item.setText(movie.getString("audience_rating"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_movie_show, menu);
		return true;
	}

}
