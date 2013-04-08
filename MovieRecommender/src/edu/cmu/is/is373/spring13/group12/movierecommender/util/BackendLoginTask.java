package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import com.facebook.Request;
import com.facebook.Session;

import android.os.AsyncTask;

public class BackendLoginTask extends AsyncTask<UserSession, Void, Void> {
	@Override
	protected Void doInBackground(UserSession... params) {
		Request.newMeRequest(Session.getActiveSession(), new FacebookCallback(params[0])).executeAndWait();
		return null;
	}
}
