package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.OnScrollListener;

public class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private String query;
    private ArrayAdapter<String> adapter;
    private List<Integer> ids;
    private Activity activity;

    public EndlessScrollListener(Activity activity, String query, ArrayAdapter<String> adapter, List<Integer> ids) {
    	this.query = query;
    	this.adapter = adapter;
    	this.ids = ids;
    	this.activity = activity;
    	addNextPage();
    }
    
    public EndlessScrollListener(int visibleThreshold, Activity activity, String query, ArrayAdapter<String> adapter, List<Integer> ids) {
        this(activity, query, adapter, ids);
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
            if(totalItemCount == 0) {
            	adapter.add("No results found");
            	ids.add(-1);
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			addNextPage();
        }
    }
    
    private void addNextPage() {
    	try {
			loading = true;
			String json = new SearchMoviesTask().execute(query, Integer.toString(currentPage + 1)).get();
            JSONArray movies = new JSONObject(json).getJSONArray("movies");
			
			for(int i = 0; i < movies.length(); i++) {
				JSONObject movie = movies.getJSONObject(i);
				adapter.add(movie.getString("name"));
				ids.add(movie.getInt("id"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			Builder builder = new AlertDialog.Builder(activity);
		    builder.setTitle("Whoops"); 
		    builder.setMessage("Something went wrong converting the server response.");
		    builder.setPositiveButton("ok", null);
		    builder.show();
		}
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}
