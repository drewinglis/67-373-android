package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import edu.cmu.is.is373.spring13.group12.movierecommender.R;

public class FacebookCallback implements Session.StatusCallback {
	private Activity activity;
	private UserSession userSession;
	private boolean loggedIn;
	
	public FacebookCallback(Activity activity, UserSession userSession) {
		this.activity = activity;
		this.userSession = userSession;
		loggedIn = false;
	}
	
	@Override
	public void call(Session session, SessionState state, Exception exception) {
		if(session.isOpened()) {

			// make request to the /me API
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
				// callback after Graph API response with user object
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if(user != null) {
						TextView welcome = (TextView)activity.findViewById(R.id.welcome);
						welcome.setText("Welcome, " + user.getName() + "!");
						
						if(!loggedIn) {
							try {
								Session session = Session.getActiveSession();
								List<String> permissions = session.getPermissions();
								List<String> newPermissions = new LinkedList<String>(permissions);
								newPermissions.add("email");
								newPermissions.add("user_birthday");
								newPermissions.add("user_likes");
								newPermissions.add("friends_likes");
								newPermissions.add("user_interests");
								newPermissions.add("friends_interests");
								if(!permissions.containsAll(newPermissions)) {
									Session.NewPermissionsRequest req = new Session.NewPermissionsRequest(activity, newPermissions);
									session.requestNewReadPermissions(req);
								}
								
								String json = new BackendLoginTask().execute((String)user.getProperty("email"), user.getName(), session.getAccessToken(), user.getId(), Integer.toString(getAge(user.getBirthday()))).get();
								JSONObject data = new JSONObject(json);
								userSession.set(Integer.toString(data.getInt("user_id")), data.getString("token"));
								loggedIn = true;
							} catch (Exception e) {
								Builder builder = new AlertDialog.Builder(activity);
							    builder.setTitle("Whoops"); 
							    builder.setMessage("Something went wrong converting the server response.");
							    builder.setPositiveButton("ok", null);
							    builder.show();
							}
						}
					}
				}
			});
		}
	}
	
  	private int getAge(String birthday) {
  		Calendar dob = Calendar.getInstance();
		dob.set(Calendar.HOUR_OF_DAY, 0);
		dob.set(Calendar.MINUTE, 0);
		dob.set(Calendar.SECOND, 0);
		dob.set(Calendar.MILLISECOND, 0);
		String[] parts = birthday.split("/");
		dob.set(Calendar.MONTH, Integer.parseInt(parts[0]));
		dob.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[1]));
		dob.set(Calendar.YEAR, Integer.parseInt(parts[2]));
		
  		Calendar today = Calendar.getInstance();
  		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
  		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
  			age--;
  		}
  		return age;
  	}
}
