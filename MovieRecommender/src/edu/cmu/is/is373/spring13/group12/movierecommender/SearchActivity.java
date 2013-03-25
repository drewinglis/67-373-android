package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.io.IOException;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.MoviesAPI;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void searchMovies(View button) {
		EditText movieField = (EditText) findViewById(R.id.moviesSearchBox);  
		String name = movieField.getText().toString();
		try {
			String json = MoviesAPI.getDummyMoviesFromServer(name, 1, this);
			Intent searchIntent = new Intent(this, MovieListActivity.class);
			searchIntent.putExtra("json", json);
			startActivity(searchIntent);
		}
		catch(IOException e) {
		    Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Whoops"); 
		    builder.setMessage("We failed to connect to the server.");
		    builder.setPositiveButton("ok", null);
		    builder.show();
		}
	}
}
