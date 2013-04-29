package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.EndlessScrollListener;
import edu.cmu.is.is373.spring13.group12.movierecommender.util.GetRecommendationsTask;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MovieListActivity extends ListActivity {
	private List<Integer> ids;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_movie_list);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
	    setListAdapter(adapter);        
	    getListView().setTextFilterEnabled(true);
	    
	    ids = new ArrayList<Integer>();

	    Bundle info = getIntent().getExtras();
	    if(info.getString("intent").equals("search")) {
			try {
				this.getListView().setOnScrollListener(new EndlessScrollListener(2, this, info.getString("query"), adapter, ids));
			} catch (Exception e) {
				Log.e("Exception", "MovieListActivity", e);
				Builder builder = new AlertDialog.Builder(this);
			    builder.setTitle("Whoops"); 
			    builder.setMessage("Something went wrong converting the server response.");
			    builder.setPositiveButton("ok", null);
			    builder.show();
			}
	    }
	    
	    else if(info.getString("intent").equals("recommendations")) {
	    	try {
		    	String json = new GetRecommendationsTask().execute((String)info.getString("id"), (String)info.get("token")).get();
		    	JSONArray movies = new JSONObject(json).getJSONArray("movies");
				
				for(int i = 0; i < movies.length(); i++) {
					JSONObject movie = movies.getJSONObject(i);
					adapter.add(movie.getString("name"));
					ids.add(movie.getInt("id"));
				}
				if(adapter.isEmpty()) {
					adapter.add("No recommendations found");
					ids.add(-1);
				}
	    	} catch (Exception e) {
				Builder builder = new AlertDialog.Builder(this);
			    builder.setTitle("Whoops"); 
			    builder.setMessage("Something went wrong converting the server response.");
			    builder.setPositiveButton("ok", null);
			    builder.show();
			}
	    }
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		if(ids.get(position) == -1) {
			return;
		}
		Intent showIntent = new Intent(this, MovieShowActivity.class);
		showIntent.putExtra("id", ids.get(position));
		startActivity(showIntent);
	}
}
