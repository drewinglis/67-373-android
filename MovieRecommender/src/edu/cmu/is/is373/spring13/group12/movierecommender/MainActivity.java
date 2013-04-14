package edu.cmu.is.is373.spring13.group12.movierecommender;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.UserSession;

public class MainActivity extends FragmentActivity {
	private MainFragment mainFragment;
	private UserSession userSession;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {	    	
	        // Add the fragment on initial activity setup
	    	userSession = new UserSession();
	        mainFragment = new MainFragment();
	        mainFragment.setParams(this, userSession);
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	    	userSession = (UserSession)savedInstanceState.getSerializable("userSession");
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	        mainFragment.setParams(this, userSession);
	    }
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    savedInstanceState.putSerializable("userSession", userSession);
	}
	
  	public void goSearch(View button) {
  		Intent searchIntent = new Intent(this, SearchActivity.class);
		startActivity(searchIntent);
  	}
  	
  	public void goRecommendation(View button) {
  		if(userSession.getId() == null) {
  			Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Whoops"); 
		    builder.setMessage("Give us a moment to complete your login.");
		    builder.setPositiveButton("ok", null);
		    builder.show();
  		}
  		else {
	  		Intent recIntent = new Intent(this, MovieListActivity.class);
	  		recIntent.putExtra("intent", "recommendations");
			recIntent.putExtra("token", userSession.getToken());
			recIntent.putExtra("id", userSession.getId());
			startActivity(recIntent);
  		}
  	}
}