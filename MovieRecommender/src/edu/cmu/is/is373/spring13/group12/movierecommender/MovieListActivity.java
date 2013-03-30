package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.SearchMoviesTask;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MovieListActivity extends ListActivity {
	private ArrayList<String> data;
	private JSONArray movies;
	private int page;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);      
	    data = new ArrayList<String>();

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
	    setListAdapter(adapter);        
	    getListView().setTextFilterEnabled(true);
	    
	    
	    
	    Bundle info = getIntent().getExtras();
	    page = 1;
	    if(info.getBoolean("search")) {
			try {
				String json = new SearchMoviesTask().execute(info.getString("query"), Integer.toString(page)).get();
				
				movies = new JSONObject(json).getJSONArray("movies");
				
				for(int i = 0; i < movies.length(); i++) {
					JSONObject movie = movies.getJSONObject(i);
					adapter.add(movie.getString("name"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Builder builder = new AlertDialog.Builder(this);
			    builder.setTitle("Whoops"); 
			    builder.setMessage("Something went wrong converting the server response.");
			    builder.setPositiveButton("ok", null);
			    builder.show();
			}

	    }
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		try {
			Intent showIntent = new Intent(this, MovieShowActivity.class);
			showIntent.putExtra("id", movies.getJSONObject(position).getInt("id"));
			startActivity(showIntent);
		} catch (JSONException e) {
			Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Whoops"); 
		    builder.setMessage("Something went wrong converting the server response.");
		    builder.setPositiveButton("ok", null);
		    builder.show();
		}
	}
}
