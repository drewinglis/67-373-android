package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import com.facebook.Session;
import com.facebook.SessionState;

public class OpenCallback implements Session.StatusCallback {
	private UserSession userSession;
	
	public OpenCallback(UserSession userSession) {
		this.userSession = userSession;
	}
	
	@Override
	public void call(Session session, SessionState state, Exception exception) {
		new BackendLoginTask().execute(userSession);
	}
}
