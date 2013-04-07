package edu.cmu.is.is373.spring13.group12.movierecommender;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.facebook.*;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.FacebookCallback;
import edu.cmu.is.is373.spring13.group12.movierecommender.util.UserSession;

public class MainActivity extends Activity {
	private UserSession userSession;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userSession = new UserSession();
		
		Session.openActiveSession(this, true, new FacebookCallback(this, userSession));
	}

  	@Override
  	public void onActivityResult(int requestCode, int resultCode, Intent data) {
  		super.onActivityResult(requestCode, resultCode, data);
  		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  	}

  	public void goSearch(View button) {
  		Intent searchIntent = new Intent(this, SearchActivity.class);
		startActivity(searchIntent);
  	}
  	
  	public void goRecommendation(View button) {
  		Intent recIntent = new Intent(this, MovieListActivity.class);
  		recIntent.putExtra("intent", "recommendations");
		recIntent.putExtra("token", userSession.getToken());
		recIntent.putExtra("id", userSession.getId());
		startActivity(recIntent);
  	}
}