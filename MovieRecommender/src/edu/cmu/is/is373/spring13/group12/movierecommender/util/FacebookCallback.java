package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.util.Calendar;

import org.json.JSONObject;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class FacebookCallback implements Request.GraphUserCallback {
	private UserSession userSession;
	
	public FacebookCallback(UserSession userSession) {
		this.userSession = userSession;
	}
	
	@Override
	public void onCompleted(GraphUser user, Response response) {
		if(user != null) {
			try {
				Session session = Session.getActiveSession();
				String json = MoviesAPI.sendFacebookLogin((String)user.getProperty("email"), user.getName(), session.getAccessToken(), user.getId(), Integer.toString(getAge(user.getBirthday())));
				JSONObject data = new JSONObject(json);
				userSession.set(Integer.toString(data.getInt("user_id")), data.getString("token"));
			} catch(Exception e) {
				Log.e("Exception", "FacebookCallback", e);
			}
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
