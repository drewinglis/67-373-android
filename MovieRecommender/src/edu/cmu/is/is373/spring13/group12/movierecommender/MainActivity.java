package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.internal.Utility;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.OpenCallback;
import edu.cmu.is.is373.spring13.group12.movierecommender.util.UserSession;

public class MainActivity extends Activity {
	private UserSession userSession;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userSession = new UserSession(this);
		
		Session session = Session.getActiveSession();
        if (session == null || session.getState().isClosed()) {
        	String applicationId = Utility.getMetadataApplicationId(this);
            session = new Session.Builder(this).setApplicationId(applicationId).build();
            Session.setActiveSession(session);
        }
        if (!session.isOpened()) {
            Session.OpenRequest openRequest = new Session.OpenRequest(this);

            openRequest.setDefaultAudience(SessionDefaultAudience.NONE);
			List<String> permissions = new ArrayList<String>();
			permissions.add("email");
			permissions.add("user_birthday");
			permissions.add("user_likes");
			permissions.add("friends_likes");
			permissions.add("user_interests");
			permissions.add("friends_interests");
            openRequest.setPermissions(permissions);
            openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
            openRequest.setCallback(new OpenCallback(userSession));

            session.openForRead(openRequest);
        }
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