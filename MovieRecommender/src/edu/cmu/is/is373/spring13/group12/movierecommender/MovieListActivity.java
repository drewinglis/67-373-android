package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);      
	    data = new ArrayList<String>();

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
	    setListAdapter(adapter);        
	    getListView().setTextFilterEnabled(true);
	    
	    Bundle info = getIntent().getExtras();
	    try {
			movies = new JSONArray(info.getString("json"));
			for(int i = 0; i < movies.length(); i++) {
				JSONObject movie = movies.getJSONObject(i);
				String id = movie.getString("name");
				String date = movie.getString("release_date");
				if(date != null & date.length() > 0) {
					id += " (" + movie.getString("release_date") + ")";
				}
				adapter.add(id);
			}
		} catch (JSONException e) {
			Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Whoops"); 
		    builder.setMessage("Something went wrong converting the server response.");
		    builder.setPositiveButton("ok", null);
		    builder.show();
		}
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		try {
			Intent showIntent = new Intent(this, MovieShowActivity.class);
			showIntent.putExtra("json", movies.getJSONObject(position).toString());
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
