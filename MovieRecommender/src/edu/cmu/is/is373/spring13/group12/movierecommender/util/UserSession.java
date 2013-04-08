package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import android.app.Activity;
import edu.cmu.is.is373.spring13.group12.movierecommender.MainActivity;

public class UserSession {
	private MainActivity logginActivity;
	private String token;
	private String id;
	
	public UserSession(MainActivity logginActivity) {
		this.logginActivity = logginActivity;
	}
	
	public void set(String id, String token) {
		this.id = id;
		this.token = token;
	}
	
	public String getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}
	
	public Activity getActivity() {
		return logginActivity;
	}
}
