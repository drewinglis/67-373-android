package edu.cmu.is.is373.spring13.group12.movierecommender;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
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
		if(name == null || name.length() < 1) {
			return;
		}
		
		Intent searchIntent = new Intent(this, MovieListActivity.class);
		searchIntent.putExtra("search", true);
		searchIntent.putExtra("query", name);
		startActivity(searchIntent);
	}
}
