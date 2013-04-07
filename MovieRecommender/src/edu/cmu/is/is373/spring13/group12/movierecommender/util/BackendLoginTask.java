package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.os.AsyncTask;

public class BackendLoginTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		try {
			return MoviesAPI.sendFacebookLogin(params[0], params[1], params[2], params[3], params[4]);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
